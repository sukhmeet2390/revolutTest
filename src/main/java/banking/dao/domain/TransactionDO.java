package banking.dao.domain;

import banking.api.dto.response.Transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class TransactionDO {
    private String id;
    private Long from;
    private Long to;
    private Long time;
    private Double amount;
    private TransactionType transactionType;
    private Double totalBalance;

}