package banking.consumer;

import banking.api.dto.response.Balance;
import banking.api.dto.response.Profile;
import banking.dao.BalanceDao;
import banking.dao.impl.BalanceDaoImpl;
import banking.mappers.BalanceMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
