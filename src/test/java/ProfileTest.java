import banking.api.dto.exception.ClientException;
import banking.api.dto.request.CreateProfileRequest;
import banking.api.dto.response.Mobile;
import banking.api.dto.response.Name;
import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import org.junit.Test;

public class ProfileTest extends BankingTest {
    @Test
    public void testAccountCreation() {
        Profile profile = profileConsumer.createUser(getPublicProfile());
        assertEquals(profile.id.longValue(), 1);
    }

    public void testWrongNameDataCreation() {
        PublicProfile profile = new PublicProfile(
                null,
                "n@r.com",
                new Mobile("91", "8004968195"));
        try {
            new CreateProfileRequest(profile).validate();
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ClientException.class);
        }
    }

    public void testWrongFirstNameDataCreation() {
        PublicProfile profile = new PublicProfile(
                new Name("", "Singh"),
                "s@s.com",
                new Mobile("91", "8004968195"));
        try {
            new CreateProfileRequest(profile).validate();
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ClientException.class);
        }
    }

    public void testWrongMobileDataCreation() {
        try {
            new Mobile("", "8004968195").validate();
        } catch (Exception ex) {
            assertEquals(ex.getClass(), ClientException.class);
        }
    }

    @Test
    public void testGetUserById() {
        PublicProfile publicProfile = getPublicProfile();
        profileConsumer.createUser(publicProfile);
        PublicProfile gotProfile = profileConsumer.getUserById(1L);
        assertEquals(publicProfile.name, gotProfile.name);
    }

}