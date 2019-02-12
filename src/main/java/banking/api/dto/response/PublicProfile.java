package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;


@ToString
@EqualsAndHashCode
public class PublicProfile implements Response {
    @NotNull
    public final Name name;
    public final String email;
    public final Mobile mobile;

    @JsonCreator
    public PublicProfile(@NotNull @JsonProperty("name") Name name,
                         @NotNull @JsonProperty("email") String email,
                         @NotNull @JsonProperty("mobile") Mobile mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

}
