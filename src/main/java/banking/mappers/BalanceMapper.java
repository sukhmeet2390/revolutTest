package banking.mappers;

import banking.api.dto.response.Balance;
import banking.dao.domain.BalanceDO;

public class BalanceMapper {
    public static Balance balanceDoToDto(BalanceDO balanceDO) {
        return new Balance(balanceDO.getAmount());
    }
}
