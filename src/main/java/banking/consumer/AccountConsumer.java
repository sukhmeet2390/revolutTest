package banking.consumer;

import banking.api.dto.response.Balance;
import banking.api.dto.response.Profile;
import banking.api.dto.response.Statement;
import banking.dao.BalanceDao;
import banking.dao.domain.BalanceDO;
import banking.dao.domain.TransactionDO;
import banking.dao.impl.BalanceDaoImpl;
import banking.mappers.BalanceMapper;
import banking.mappers.StatementMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class AccountConsumer {
    private final ProfileConsumer profileConsumer;
    private final Logger logger = LoggerFactory.getLogger(AccountConsumer.class);
    private final BalanceDao balanceDao;

    @Inject
    public AccountConsumer(ProfileConsumer profileConsumer, BalanceDaoImpl balanceDao) {
        this.profileConsumer = profileConsumer;
        this.balanceDao = balanceDao;
    }

    public Balance getBalance(Long id) {
        logger.info("Received request to getBalance for user: {}", id);
        Profile user = profileConsumer.getUserById(id);
        Balance balance = BalanceMapper.balanceDoToDto(
                balanceDao.getOrCreateBalance(user)
        );
        logger.info("Completed request to getBalance for user: {}", id);
        return balance;
    }

    public Statement getStatement(Long id, Long startTime, Long endTime, Integer pageCount) {
        logger.info("Received request to getStatement for user: {}, start : {}," +
                " end: {}, count: {}", id, startTime, endTime, pageCount);
        Profile user = profileConsumer.getUserById(id);
        List<TransactionDO> transactions = balanceDao.getTrimmedTransactions(user, startTime, endTime, pageCount);
        BalanceDO balance = balanceDao.getOrCreateBalance(user);
        Statement statement = StatementMapper.toStatementDto(
                user,
                BalanceMapper.balanceDoToDto(balance),
                transactions);
        logger.info("Completed request to getStatement for user: {}", id);
        return statement;
    }

    public Balance addBalance(Long userId, Double amount) {
        logger.info("Received request to addBalance for user: {}", userId); // hide acc data
        Profile profile = profileConsumer.getUserById(userId);
        addTransaction(
                userId,
                balanceDao.addBalance(profile, amount)
        );
        Balance balance = getBalance(userId);
        logger.info("Completed request to addBalance for user: {}", userId);
        return balance;
    }

    public Balance deductBalance(Long userId, Double amount) {
        logger.info("Received request to deductBalance for user: {}", userId);
        Profile profile = profileConsumer.getUserById(userId);
        addTransaction(
                userId,
                balanceDao.deductBalance(profile, amount)
        );
        Balance balance = getBalance(userId);
        logger.info("Completed request to deductBalance for user: {}", userId);
        return balance;
    }

    public Balance transferMoney(Long fromUserId, Long toUserId, Double amount) {
        logger.info("Received request to transferBalance from user: {}, to: {}", fromUserId, toUserId);
        Profile fromUser = profileConsumer.getUserById(fromUserId);
        Profile toUser = profileConsumer.getUserById(toUserId);
        Pair<TransactionDO, TransactionDO> transactionPair = balanceDao.transferBalance(fromUser, toUser, amount);
        addTransaction(fromUser.id, transactionPair.getKey());
        addTransaction(toUser.id, transactionPair.getValue());
        Balance balance = getBalance(fromUser.id);
        logger.info("Completed request to transferBalance from user: {}, to: {}", fromUserId, toUserId);
        return balance;
    }

    private void addTransaction(Long userId, TransactionDO transaction) {
        Profile user = profileConsumer.getUserById(userId);
        balanceDao.addTransaction(user, transaction);
    }
}
