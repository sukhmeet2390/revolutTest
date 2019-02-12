package banking.dao;

import banking.api.dto.response.Profile;
import banking.dao.domain.BalanceDO;

public interface BalanceDao {
    BalanceDO getOrCreateBalance(Profile profile);
}
