package banking.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class Profile extends PublicProfile {
    public final Long id;

    @JsonCreator
    public Profile(@JsonProperty("id") Long id,
                   @JsonProperty("profile") PublicProfile profile) {
        super(profile.name,
                profile.email,
                profile.mobile);
        this.id = id;
    }
}
