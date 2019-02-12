package banking.dao.impl;

import banking.api.dto.exception.NotFound;
import banking.api.dto.exception.OperationNotAllowed;
import banking.api.dto.response.Profile;
import banking.dao.BalanceDao;
import banking.dao.domain.BalanceDO;
import banking.dao.domain.TransactionDO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static banking.api.dto.response.Transaction.TransactionType.CREDIT;
import static banking.api.dto.response.Transaction.TransactionType.DEBIT;

@Singleton
public class BalanceDaoImpl implements BalanceDao {
    public static final Integer USER_MAP_SIZE_DEFAULT = 1000; // configurable
    //short circuiting database here
    private Map<Long, List<TransactionDO>> statementMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT);
    private Map<Long, BalanceDO> balanceMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT);

    private Map<Long, Object> lockMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT); //used to simulate row level locks in db like MySQl


    @Override
    public synchronized BalanceDO getOrCreateBalance(Profile profile) {
        BalanceDO balanceDO = balanceMap.get(profile.id);
        if (balanceDO == null) {
            Long now = System.nanoTime() / 1000;
            balanceDO = new BalanceDO(0.0, now, now);
            balanceMap.put(profile.id, balanceDO);
            lockMap.put(profile.id, new Object());
        }
        return balanceDO;
    }

    public List<TransactionDO> getTrimmedTransactions(Profile user, Long start, Long end, int limit) {
        return getSubList(getTransaction(user), start, end, limit);
    }

    private static List<TransactionDO> getSubList(List<TransactionDO> list, Long start, Long end, int limit) {
        List<TransactionDO> transactions = new ArrayList<>(limit);
        int count = 0;
        for (int i = 0; i < list.size() && count < limit; i++) {
            if (list.get(i).getTime() >= start
                    && list.get(i).getTime() < end) {
                transactions.add(list.get(i));
                count++;
            }
        }
        return transactions;
    }

    private List<TransactionDO> getTransaction(Profile user) {
        if (!statementMap.containsKey(user.id)) {
            statementMap.put(user.id, new ArrayList<>());
        }
        getOrCreateBalance(user);
        List<TransactionDO> transactions = statementMap.get(user.id);
        return transactions;
    }

    @Override
    public TransactionDO addBalance(Profile user, Double amount) {
        Long now = System.nanoTime() / 1000;
        BalanceDO balanceDO = getOrCreateBalance(user);
        Double totalBalance;
        if (balanceDO == null) {
            throw new NotFound("User Id cannot be located!");
        }
        Double updateAmount = amount;
        //lock this user balance
        synchronized (lockMap.get(user.id)) {
            totalBalance = balanceDO.getAmount() + updateAmount;
            balanceDO.setAmount(totalBalance);
            balanceDO.setUpdatedOn(now);
        }
        return new TransactionDO(UUID.randomUUID().toString(), null, user.id, now, updateAmount, CREDIT, totalBalance);
    }

    @Override
    public TransactionDO deductBalance(Profile user, Double amount) {
        Long now = System.nanoTime() / 1000;
        BalanceDO balanceDO = getOrCreateBalance(user);
        Double totalBalance;
        if (balanceDO == null) {
            throw new NotFound("User Id cannot be located!");
        }
        Double updateAmount = amount;
        //lock this user balance
        synchronized (lockMap.get(user.id)) {
            if (balanceDO.getAmount() < updateAmount) {
                throw new OperationNotAllowed("Insufficient Funds!");
            }
            totalBalance = balanceDO.getAmount() - updateAmount;
            balanceDO.setAmount(totalBalance);
            balanceDO.setUpdatedOn(now);
        }
        return new TransactionDO(UUID.randomUUID().toString(), user.id, null, now, updateAmount, DEBIT, totalBalance);
    }

    @Override
    public Pair<TransactionDO, TransactionDO> transferBalance(Profile fromUser, Profile toUser, Double amount) {
        String transactionId = UUID.randomUUID().toString();
        Long now = System.nanoTime() / 1000;
        BalanceDO fromBalanceDO = getOrCreateBalance(fromUser);
        if (fromBalanceDO == null) {
            throw new NotFound("Sender User Id cannot be located!");
        }
        BalanceDO toBalanceDO = getOrCreateBalance(toUser);
        if (toBalanceDO == null) {
            throw new NotFound("Receiver User Id cannot be located!");
        }
        Double updateAmount = amount;
        Double fromTotalAmount, toTotalAmount;

        if (!fromUser.equals(toUser)) {//else deadlock
            //lock both user balance, deadlock free ordered manner
            Long min = fromUser.id.compareTo(toUser.id) > 0 ? fromUser.id : toUser.id;
            Long max = fromUser.id.compareTo(toUser.id) < 0 ? fromUser.id : toUser.id;
            synchronized (lockMap.get(min)) {
                synchronized (lockMap.get(max)) {
                    if (fromBalanceDO.getAmount() < updateAmount) {
                        throw new OperationNotAllowed("Insufficient Funds!");
                    }
                    fromTotalAmount = fromBalanceDO.getAmount() - updateAmount;
                    toTotalAmount = toBalanceDO.getAmount() + updateAmount;
                    fromBalanceDO.setAmount(fromTotalAmount);
                    fromBalanceDO.setUpdatedOn(now);
                    toBalanceDO.setAmount(toTotalAmount);
                    toBalanceDO.setUpdatedOn(now);
                }
            }
        } else {
            fromTotalAmount = fromBalanceDO.getAmount();
            toTotalAmount = fromTotalAmount;
        }
        return new ImmutablePair<>(
                new TransactionDO(transactionId, fromUser.id, toUser.id, now, updateAmount, DEBIT, fromTotalAmount),
                new TransactionDO(transactionId, fromUser.id, toUser.id, now, updateAmount, CREDIT, toTotalAmount));
    }

    @Override
    public void addTransaction(Profile user, TransactionDO transaction) {
        synchronized (lockMap.get(user.id)) {
            getTransaction(user).add(transaction);
            getTransaction(user).sort((t1, t2) -> t2.getTime().compareTo(t1.getTime()));
        }
    }

}
