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
}
