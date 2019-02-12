package banking.api.dto.request;

import banking.api.dto.exception.ClientException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@ToString
public class TransferBalanceRequest implements Request {
    public final Long fromUserId;
    public final Long toUserId;
    public final Double amount;
    public final String token; // not used for now

    @JsonCreator
    public TransferBalanceRequest(@JsonProperty("fromUserId") Long fromUserId,
                                  @JsonProperty("toUserId") Long toUserId,
                                  @JsonProperty("amount") Double amount,
                                  @JsonProperty("token") String token) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.token = token;
    }

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (fromUserId == null || fromUserId <= 0) {
            errors.add("FromUser id cannot be blank");
        }

        if (toUserId == null || fromUserId <= 0) {
            errors.add("ToUser id cannot be blank");
        }

        if (amount == null || amount <= 0) {
            errors.add("Amount should be positive");
        }

        if (StringUtils.isBlank(token)) {
            errors.add("Token can not be blank");
        }

        if (errors.size() > 0) {
            throw new ClientException(errors.toString());
        }
    }
}