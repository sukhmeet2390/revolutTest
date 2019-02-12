package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
public class Statement implements Response {
    public final Profile profile;
    public final Date generationDate;
    public final Balance balance;
    public final List<Transaction> transactions;
    public final Long nextPageEndTime; //token based mechanisms with cursor are best here

    @JsonCreator
    public Statement(@JsonProperty("profile") Profile profile,
                     @JsonProperty("generationDate") Date generationDate,
                     @JsonProperty("balance") Balance balance,
                     @JsonProperty("transactions") List<Transaction> transactions,
                     @JsonProperty("nextPageEndTime") Long nextPageEndTime) {
        this.profile = profile;
        this.generationDate = generationDate;
        this.balance = balance;
        this.transactions = transactions;
        this.nextPageEndTime = nextPageEndTime;
    }

}