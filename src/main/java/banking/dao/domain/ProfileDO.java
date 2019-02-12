package banking.dao.domain;

import banking.api.dto.response.Mobile;
import banking.api.dto.response.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class ProfileDO {
    private Long id;
    private Name name;
    private String email;
    private Mobile mobile;
    private Long createdOn;
    private Long updatedOn;

    public ProfileDO(Long id, Name name, String email, Mobile mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
}
