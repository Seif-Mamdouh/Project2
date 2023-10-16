import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class AccountDatabaseTest {
    private AccountDatabase accountDatabase;
    private Account account1;
    private Account account2;
    private Account account3;

    @Before
    public void setUp() {
        accountDatabase = new AccountDatabase();
        account1 = new Savings(new Profile("Seif", "Mamdouh", new Date(1, 1, 2000)), 1000.0, true);
        account2 = new Checking(new Profile("Will", "Smith", new Date(2, 2, 1995)), 500.0);
        account3 = new Checking(new Profile("Jada", "Pinkett", new Date(2, 2, 2000)), 1500.0);
        accountDatabase.open(account1);
        accountDatabase.open(account2);
    }



    @Test
    public void onlyHasTwoAccounts(){
        // Ensure the accounts are opened
        assertEquals(2, accountDatabase.getNumAccounts());
    }

    @Test
    public void closeBothAccounts(){
        // Close account1
        assertTrue(accountDatabase.close(account1));
        assertEquals(1, accountDatabase.getNumAccounts());
        assertFalse(accountDatabase.contains(account1));  // Should not contain the closed account

        // Close account2
        assertTrue(accountDatabase.close(account2));
        assertEquals(0, accountDatabase.getNumAccounts());
        assertFalse(accountDatabase.contains(account2));  // Should not contain the closed account
    }

    @Test
    public void closeNonExisitngAccount(){
        // Try to close an account that doesn't exist
        assertFalse(accountDatabase.close(account3));
    }
}

