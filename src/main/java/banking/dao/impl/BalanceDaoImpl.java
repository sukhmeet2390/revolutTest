package banking.dao.impl;

import banking.api.dto.response.Profile;
import banking.dao.BalanceDao;
import banking.dao.domain.BalanceDO;
import banking.dao.domain.TransactionDO;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class BalanceDaoImpl implements BalanceDao {
    public static final Integer USER_MAP_SIZE_DEFAULT = 1000; // configurable

    //short circuiting database here
    private Map<Long, List<TransactionDO>> statementMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT);
    private Map<Long, BalanceDO> balanceMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT);
    //used to simulate row level locks in db like MySQl
    private Map<Long, Object> lockMap = new ConcurrentHashMap<>(USER_MAP_SIZE_DEFAULT);


    @Override
    public BalanceDO getOrCreateBalance(Profile profile) {
        BalanceDO balanceDO = balanceMap.get(profile.id);
        if(balanceDO == null){
            Long now = System.nanoTime()/1000;
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

}
