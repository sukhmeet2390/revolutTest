import banking.api.dto.response.Mobile;
import banking.api.dto.response.Name;
import banking.api.dto.response.Profile;
import banking.api.dto.response.PublicProfile;
import banking.consumer.AccountConsumer;
import banking.consumer.ProfileConsumer;
import banking.dao.impl.BalanceDaoImpl;
import banking.dao.impl.ProfileDaoImpl;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class BankingTest extends TestCase {
    protected AccountConsumer accountConsumer;
    protected ProfileConsumer profileConsumer;

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        profileConsumer = new ProfileConsumer(new ProfileDaoImpl());
        accountConsumer = new AccountConsumer(profileConsumer, new BalanceDaoImpl());
    }

    @Test
    public void testSetUp() {

    }

    protected void tearDown() throws Exception {
        super.tearDown();
        profileConsumer = null;
        accountConsumer = null;
    }

    protected PublicProfile getPublicProfile() {
        return new PublicProfile(new Name("Sukhmeet", "Singh"),
                "skhmtsngh@gmail.com",
                new Mobile("91", "8004968195")
        );
    }

    protected PublicProfile getRandomPublicProfile() {
        return new PublicProfile(new Name("Random", "Singh"),
                UUID.randomUUID().toString() + "@gmail.com",
                new Mobile("91", String.valueOf(
                        (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L))
        );
    }

    protected Profile createAccount() {
        return profileConsumer.createUser(getPublicProfile());
    }
}