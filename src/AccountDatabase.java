/**
 * Contains an array of accounts and performs the operations the user desires.
 *
 * @author Seifeldeen Mohamed
 */
public class AccountDatabase {
    private Account [] accounts; //list of various types of accounts private int numAcct; //number of accounts in the array
    private int numAcct; //number of Accounts in the Array

    private final static int INITIAL_CAPACITY = 4;
    private final static int DEFAULT_CONSTRUCTOR_VAL = 0;
    private final static int NOT_FOUND = -1;

    /**
     * Get the number of accounts currently held in Account Database
     *
     * @return number of accounts in Account Database
     */
    public int getNumAccounts(){
        return numAcct;
    }

    /**
     * Default Constructor for AccountDatabase
     */
    public AccountDatabase(){
        this.accounts = new Account[INITIAL_CAPACITY];
        this.numAcct = DEFAULT_CONSTRUCTOR_VAL;
    }

    /**
     * Method to find an account in an array
     *
     * @param account account to find
     * @return NOT_FOUND if not found the array,
     * index of the account otherwise
     */
    private int find(Account account) {
        for(int i = 0; i < numAcct; i++){
            if(this.accounts[i].equals(account)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Method to increase the capacity of the array
     * by the INITIAL_CAPACITY
     */
    private void grow(){
        Account[] newAccounts = new Account[this.accounts.length + AccountDatabase.INITIAL_CAPACITY];

        for(int i = 0; i < this.accounts.length; i++) {
            newAccounts[i] = this.accounts[i];
        }

        this.accounts = newAccounts;
    }

    /**
     * Method to check if the account
     *
     * @param account account to check existence of
     * @return false if not found in array, true if it was found
     */
    public boolean contains(Account account){
        int accountToSearch = find(account);
        return accountToSearch != NOT_FOUND;
    }

    /**
     * Will check if an account already exists to make sure that the user
     * will be able to open one. This method also makes sure that the profile
     * holder also does not have a checking while trying to open a college
     * checking or vice versa
     *
     * @param account account to try to find an existing copy of.
     * @return true if account exists, false otherwise
     */
    private boolean preventedFromBeingAdded(Account account) {
        boolean normalContains = this.contains(account);
        if (normalContains || !(account instanceof Checking)) {
            return normalContains;
        }

        Account opposite = new CollegeChecking(account.profileHolder,
                                               0,
                                               Campus.NEW_BRUNSWICK
        );
        if (account instanceof CollegeChecking) {
            opposite = new Checking(account.profileHolder, 0);
        }
        return this.contains(opposite);

    }

    /**
     * A method to add a new Account in the Array.
     *
     * @param account account to open
     * @return true if successfully added to the array,
     * false if it was prevented from being added by
     * something that already exists in array
     */
    public boolean open (Account account){
        if(this.preventedFromBeingAdded(account)){
            return false;
        }

        if(this.accounts.length == numAcct){
            this.grow();
        }
        this.accounts[numAcct] = account;
        this.numAcct++;

        return true;

    }

    /**
     * Remove an account from the Array
     *
     * @param account account to remove
     * @return true if successful and false otherwise
     */
    public boolean close(Account account) {
        if (!this.contains(account)) {
            return false;
        }

        int accountToSearch = find(account);

        for (int i = accountToSearch + 1; i < this.numAcct; i++) {
            this.accounts[i - 1] = this.accounts[i];
        }

        this.numAcct--;

        return true;
    }

    /**
     * Method to deposit money into an account
     *
     * @param account the account to deposit to
     * @param depositAmount amount to deposit
     */
    public void deposit(Account account, double depositAmount){
        int accountToSearch = find(account);

        Account accountInTheDatabase = accounts[accountToSearch];

        // Call a deposit method on the account object with the specified depositAmount
        accountInTheDatabase.makeDeposit(depositAmount);

    }

    /**
     * Method to make a withdrawal
     *
     * @param account account to withdraw from
     * @param withdrawalAmount amount to withdraw from account
     * @return false if insufficient fund
     */
    public boolean withdraw(Account account, double withdrawalAmount) {
        // Check if the account exists
        if (!contains(account)) {
            return false;
        }

        int accountToSearch = find(account);
        Account accountInTheDatabase = accounts[accountToSearch];

        // Check if there's enough balance for withdrawal
        if (accountInTheDatabase.balance >= withdrawalAmount) {
            // Specify the withdrawal amount here (dynamic)
            accountInTheDatabase.makeWithdrawal(withdrawalAmount);
            return true; // Withdrawal was successful
        }

        return false; // Withdrawal not allowed or insufficient balance
    }


    /**
     * Method to print everything in the current Array
     */
    public void displayAllAccounts() {
        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];
            Profile profile = account.getProfileType();

            String firstName = profile.getFirstName();
            String lastName = profile.getLastName();
            Date dateOfBirth = profile.getDateOfBirth();

            String formattedInfo = firstName + " " + lastName + " " + dateOfBirth + " " + account.getAccountType();
            System.out.println(formattedInfo);
        }
    }

    /**
     * Sorts an array of Account objects using the bubble sort algorithm.
     */
    private void bubbleSort() {
        for (int i = 0; i < this.numAcct; i++) {
            for (int j = i; j < this.numAcct; j++) {
                if (this.accounts[j].compareTo(this.accounts[i]) < 0) {
                    Account temp = this.accounts[i];
                    this.accounts[i] = this.accounts[j];
                    this.accounts[j] = temp;
                }
            }
        }
    }


    /**
     * Method to print and sort by Account Type and Profile;
     */
    public void printSorted() {
        // Use bubbleSort to sort the accounts array
        this.bubbleSort();

        // Print the sorted accounts
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString());
        }
    }

    /**
     * Method to calculate interests and fees and display them
     */
    public void printFeesAndInterests() {
        this.bubbleSort();
        for (int i = 0; i < numAcct; i++) {
            Account account = this.accounts[i];

            String toPrint = String.format(
                    //"%s::fee $%s::monthly interest $%s",
                    "%s::fee $%,.2f::monthly interest $%,.2f",
                    account,
                    //AccountDatabase.DECIMAL_FORMAT.format(account.monthlyFee()),
                    //AccountDatabase.DECIMAL_FORMAT.format(account.monthlyInterest())
                    account.monthlyFee(),
                    account.monthlyInterest()
                    //AccountDatabase.round(account.monthlyFee()),
                    //AccountDatabase.round(account.monthlyInterest())

            );
            System.out.println(toPrint);

        }

    }

    /**
     * Method to Apply interest/fees to all accounts
     */
    public void printUpdatedBalances(){
        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];

            double monthlyInterest = account.monthlyInterest();
            double monthlyFee = account.monthlyFee();

            double updatedBalance = account.getBalance() + monthlyInterest - monthlyFee;
            account.updateBalance(updatedBalance);
        }
        this.printSorted();
    }

    /**
     * Test code for AccountDatabase
     * @param args does not take command line arguments
     */
    public static void main(String[] args) {
        AccountDatabase accountDatabase = new AccountDatabase();

        Date date1 = new Date(10, 12, 2002);
        Date date2 = new Date(1, 1, 2002);
        Date date3 = new Date(10, 10, 2002);
        Date date4 = new Date(2, 2, 2002);

        // Create some sample accounts
        Account account1 = new Savings(new Profile("Seif", "Mamdouh", date1), 1000.0, true);
        Account account2 = new MoneyMarket(new Profile("Mikey", "Muzafarov", date2), 2000.0, false);
        Account account3 = new Checking(new Profile("Mikey", "Muzafarov", date3), 2000.0);
        Account account4 = new CollegeChecking(new Profile("Mikey", "Muzafarov", date4), 100.0, Campus.NEW_BRUNSWICK);



        // Test the open method to add accounts
        accountDatabase.open(account1);
        accountDatabase.open(account2);
        accountDatabase.open(account3);
        accountDatabase.open(account4);

        //Check the number of accounts in the DB
        System.out.println("The number of amount of Accounts in the Database is: " + accountDatabase.getNumAccounts());

            //Check if accounts were added
        System.out.println("Added Account 1: "  + accountDatabase.contains(account1));

        System.out.println("Added Account 2 : " + accountDatabase.contains(account2));
        System.out.println("Added Account 3 : " + accountDatabase.contains(account3));

        // Test deposit
        double depositAmount = 500.0;
        accountDatabase.deposit(account1, depositAmount);
        System.out.println("Savings account balance after deposit: " + account1.balance);

        // Test withdrawal
        double withdrawalAmount = 1000.0;
        boolean withdrawalSuccess = accountDatabase.withdraw(account2, withdrawalAmount);
        System.out.println("Withdrawal from Money Market account: " + withdrawalSuccess);
        System.out.println("Money Market account balance after withdrawal: " + account2.balance);


        accountDatabase.printFeesAndInterests();

        accountDatabase.printSorted();
        accountDatabase.displayAllAccounts();
        accountDatabase.printUpdatedBalances();
    }

}


