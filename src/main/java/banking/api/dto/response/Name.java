package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;


@ToString
public class Name implements Response {
    public final String firstName;
    public final String lastName;

    @JsonCreator
    public Name(@NotNull @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
