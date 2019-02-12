package banking.consumer;

import banking.api.dto.exception.NotFound;
import banking.api.dto.exception.OperationNotAllowed;
import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import banking.dao.domain.ProfileDO;
import banking.dao.impl.ProfileDaoImpl;
import banking.mappers.ProfileMapper;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileConsumer {
    private final ProfileDaoImpl profileDao;
    private final Logger logger = LoggerFactory.getLogger(ProfileConsumer.class);

    @Inject
    public ProfileConsumer(ProfileDaoImpl profileDao) {
        this.profileDao = profileDao;
    }

    public Profile createUser(PublicProfile publicProfile) {
        logger.info("Received request to create user for: {}", publicProfile);
        if (profileDao.getUserByEmail(publicProfile.email) != null) {
            throw new OperationNotAllowed("User already exists with email!");
        }
        if (profileDao.getUserByMobile(publicProfile.mobile) != null) {
            throw new OperationNotAllowed("User already exists with mobile!");
        }

        ProfileDO privateProfile = profileDao.createUser(publicProfile);
        Profile profile = ProfileMapper.profileDoToDto(privateProfile);
        logger.info("Completed request to create user for: {}", publicProfile);
        return profile;
    }

    public Profile getUserById(Long userId) {
        logger.info("Received request to get user for id: {}", userId);
        ProfileDO privateProfile = profileDao.getUserById(userId);
        if (privateProfile == null) {
            throw new NotFound("User Id cannot be located! ");
        }
        Profile profile = ProfileMapper.profileDoToDto(privateProfile);
        logger.info("Completed request to get user for id: {} / {}", userId, profile);
        return profile;
    }
}
