package banking.dao.impl;

import banking.api.dto.response.Mobile;
import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import banking.dao.ProfileDao;
import banking.dao.domain.ProfileDO;
import banking.mappers.ProfileMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProfileDaoImpl implements ProfileDao {

    //short circuiting database here
    private Map<Long, ProfileDO> profileMap = new ConcurrentHashMap<>(1000);
    private Map<String, Long> emailMap = new HashMap<>(1000);
    private Map<Mobile, Long> mobileMap = new HashMap<>(1000);
    private AtomicLong counter = new AtomicLong(0);


    @Override
    public ProfileDO createUser(PublicProfile publicProfile) {
        Profile profile = getPrivateProfile(publicProfile);
        ProfileDO profileDO = ProfileMapper.profileDtoToDo(profile);

        profileMap.put(profile.id, profileDO);
        emailMap.put(profile.email, profile.id);
        mobileMap.put(profile.mobile, profile.id);

        return profileDO;
    }

    @Override
    public ProfileDO getUserById(Long id) {
        return profileMap.get(id);
    }

    @Override
    public ProfileDO getUserByEmail(String email) {
        Long userId = emailMap.get(email);
        if (userId == null) {
            return null;
        }
        return profileMap.get(userId);
    }

    @Override
    public ProfileDO getUserByMobile(Mobile mobile) {
        Long userId = mobileMap.get(mobile);
        if (userId == null) {
            return null;
        }
        return profileMap.get(userId);
    }

    private Profile getPrivateProfile(PublicProfile publicProfile) {
        Long userId = counter.incrementAndGet();
        return new Profile(userId, publicProfile);
    }
}
