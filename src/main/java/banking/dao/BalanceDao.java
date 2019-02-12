package banking.dao;

import banking.api.dto.response.Profile;
import banking.dao.domain.BalanceDO;
import banking.dao.domain.TransactionDO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface BalanceDao {
    BalanceDO getOrCreateBalance(Profile profile);

    List<TransactionDO> getTrimmedTransactions(Profile user, Long start, Long end, int limit);

    TransactionDO addBalance(Profile user, Double amount);

    TransactionDO deductBalance(Profile user, Double amount);

    Pair<TransactionDO, TransactionDO> transferBalance(Profile fromUser, Profile toUser, Double amount);

    void addTransaction(Profile user, TransactionDO transaction);
}
