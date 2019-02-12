package banking.dao;

import banking.api.dto.response.Mobile;
import banking.api.dto.response.PublicProfile;
import banking.dao.domain.ProfileDO;

public interface ProfileDao {
    ProfileDO createUser(PublicProfile publicProfile);

    ProfileDO getUserById(Long id);

    ProfileDO getUserByMobile(Mobile mobile);

    ProfileDO getUserByEmail(String email);
}
