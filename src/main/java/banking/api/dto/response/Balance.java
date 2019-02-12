package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class Balance implements Response {
    public final Double balance;

    @JsonCreator
    public Balance(@JsonProperty("balance") Double balance) {
        this.balance = balance;
    }
}
