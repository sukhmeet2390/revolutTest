package banking.api.dto.response;

import banking.api.dto.exception.ClientException;
import banking.api.dto.request.Request;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class Mobile implements Request {
    public final String countryCode;
    public final String number;
    public final Boolean isVerified; // short circuited for now

    @JsonCreator
    public Mobile(@NotNull @JsonProperty("countryCode") String countryCode,
                  @NotNull @JsonProperty("firstName") String number) {
        this.countryCode = countryCode;
        this.number = number;
        this.isVerified = true;
    }

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(countryCode)) {
            errors.add("User mobile number not valid(Country Code cannot be blank)");
        }
        if (StringUtils.isBlank(number)) {
            errors.add("User mobile number not valid(Extension number cannot be blank)");
        }
        if (!(countryCode.matches("\\d{3}") || countryCode.matches("\\d{2}"))) {
            errors.add("User mobile number not valid(Country Code not valid. Must be 2-3 digit string)");
        }
        if (!(number.matches("\\d{10}")
                || number.matches("\\d{7}")
                || number.matches("\\d{8}")
                || number.matches("\\d{9}"))) {
            errors.add("User mobile number not valid(Number not valid. Must be 7-10 digit string)");
        }
        if (errors.size() > 0) {
            throw new ClientException(errors.toString());
        }
    }


}
