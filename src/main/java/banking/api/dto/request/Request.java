package banking.api.dto.request;

import java.io.Serializable;

public interface Request extends Serializable {
    void validate();
}
