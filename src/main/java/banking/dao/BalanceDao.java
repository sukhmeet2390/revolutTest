package banking.dao;

import banking.api.dto.response.Profile;
import banking.dao.domain.BalanceDO;
import banking.dao.domain.TransactionDO;

import java.util.List;

public interface BalanceDao {
    BalanceDO getOrCreateBalance(Profile profile);

    List<TransactionDO> getTrimmedTransactions(Profile user, Long start, Long end, int limit);
}
