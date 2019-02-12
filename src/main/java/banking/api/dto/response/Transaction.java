package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.Date;

@ToString
public class Transaction implements Response {
    public final String id;
    public final Long from;
    public final Long to;
    public final Date date;
    public final Double amount;//pretty printed for different locale
    public final TransactionType transactionType;
    public final Double balance;

    @JsonCreator
    public Transaction(@JsonProperty("id") String id,
                       @JsonProperty("from") Long from,
                       @JsonProperty("to") Long to,
                       @JsonProperty("date") Date date,
                       @JsonProperty("amount") Double amount,
                       @JsonProperty("transactionType") TransactionType transactionType,
                       @JsonProperty("balance") Double balance) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.amount = amount;
        this.transactionType = transactionType;
        this.balance = balance;
    }

    public enum TransactionType {
        DEBIT, CREDIT
    }
}
