package banking.dao.domain;

import banking.api.dto.response.Transaction.TransactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TransactionDO {
    private String id;
    private Long from;
    private Long to;
    private Long time;
    private Double amount;
    private TransactionType transactionType;
    private Double totalBalance;

    public TransactionDO(String id, Long from, Long to, Long time, Double amount, TransactionType transactionType, Double totalBalance) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.time = time;
        this.amount = amount;
        this.transactionType = transactionType;
        this.totalBalance = totalBalance;
    }
}