import banking.api.dto.exception.OperationNotAllowed;
import org.junit.Test;

public class AccountsTest extends BankingTest {
    @Test
    public void testAddBalance() {
        createAccount();
        accountConsumer.addBalance(1L, 10.0);
    }

    @Test
    public void testGetBalance() {
        createAccount();
        accountConsumer.addBalance(1L, 10.0);
        assertEquals(10.0, accountConsumer.getBalance(1L).balance);
    }

    @Test
    public void testGetBalanceWrongInput() {
        createAccount();
        try {
            accountConsumer.addBalance(1L, -10.0);
        } catch (Exception ae) {
            assertEquals(OperationNotAllowed.class, ae.getClass());
        }
    }

    @Test
    public void testDeductBalance() {
        createAccount();
        accountConsumer.addBalance(1L, 10.0);
        accountConsumer.deductBalance(1L, 5.0);
        assertEquals(5.0, accountConsumer.getBalance(1L).balance);
    }

    @Test
    public void testDeductBalanceException() {
        createAccount();
        accountConsumer.addBalance(1L, 10.0);
        try {
            accountConsumer.deductBalance(1L, 15.0);
        } catch (Exception ae) {
            assertEquals(OperationNotAllowed.class, ae.getClass());
        }
    }

    @Test
    public void testTransferMoney() {
        profileConsumer.createUser(getRandomPublicProfile());
        profileConsumer.createUser(getRandomPublicProfile());
        accountConsumer.addBalance(1L, 100.0);
        accountConsumer.transferMoney(1L, 2L, 1.0);
        assertEquals(99.0,
                accountConsumer.getBalance(1L).balance);
    }

    @Test
    public void testGetStatement() {
        profileConsumer.createUser(getRandomPublicProfile());
        profileConsumer.createUser(getRandomPublicProfile());
        accountConsumer.addBalance(1L, 100.0);
        accountConsumer.transferMoney(1L, 2L, 1.0);
        accountConsumer.transferMoney(1L, 2L, 1.0);
        assertEquals(3,
                accountConsumer.getStatement(1L, 0L, Long.MAX_VALUE, 10).transactions.size());
        assertEquals(2,
                accountConsumer.getStatement(2L, 0L, Long.MAX_VALUE, 10).transactions.size());
        assertEquals(1,
                accountConsumer.getStatement(1L, 0L, Long.MAX_VALUE, 1).transactions.size());

    }
}
