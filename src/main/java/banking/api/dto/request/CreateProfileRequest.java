package banking.api.dto.request;

import banking.api.dto.exception.ClientException;
import banking.api.dto.response.PublicProfile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CreateProfileRequest implements Request {
    public final PublicProfile profile;
    private static final Pattern VALID_EMAIL_REGEX = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");

    @JsonCreator
    public CreateProfileRequest(@NotNull @JsonProperty("profile") PublicProfile profile) {
        this.profile = profile;
    }

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (profile.name == null) {
            errors.add("User name cannot be blank");
        } else if (StringUtils.isBlank(profile.name.firstName)) {
            errors.add("User first name cannot be blank");
        }
        if (profile.mobile == null) {
            errors.add("User mobile cannot be null");
        } else {
            profile.mobile.validate();
        }
        if (StringUtils.isBlank(profile.email)) {
            errors.add("User email cannot be blank");
        } else {
            if (!VALID_EMAIL_REGEX.matcher(profile.email).matches()) {
                errors.add("User email format not accepted");
            }
        }
        if (errors.size() > 0) {
            throw new ClientException(errors.toString());
        }
    }
}