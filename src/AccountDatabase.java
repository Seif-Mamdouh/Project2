public class AccountDatabase {
    private Account [] accounts; //list of various types of accounts private int numAcct; //number of accounts in the array
    private int numAcct; //number of Accounts in the Array

    private final static int INITIAL_CAPACITY = 4;
    private final static int DEFAULT_CONSTRUCTOR_VAL = 0;
    private final static int NOT_FOUND = -1;

    public int getNumAccounts(){
        return numAcct;
    };

    /**
     * Default Constructor for AccountDatabase
     */
    public AccountDatabase (){
        this.accounts = new Account[INITIAL_CAPACITY];
        this.numAcct = DEFAULT_CONSTRUCTOR_VAL;
    }

    /**
     * Method = to find a account in a array
     * @param account
     * @return NOT_FOUND if not found the array;
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
     * Method to check if the the account
     * @param account
     * @return
     */
    public boolean contains(Account account){
        int accountToSearch = find(account);
        return accountToSearch != NOT_FOUND;
    }

    /**
     * A method to add a new Account in the Array.
     * @param account
     * @return true if succesfully added to the array
     */
    public boolean open (Account account){
        if(this.accounts.length == numAcct){
            this.grow();
        }
        this.accounts[numAcct] = account;
        this.numAcct++;

        return true;

    }

    /**
     * Remove a account from the Array
     * @param account
     * @return
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





//    public boolean withdraw(Account account){
//        return false;
//    } //false if insufficient fund
//
//    public void deposit(Account account){}
//
//    public void printSorted(){} //sort by account type and profile
//    public void printFeesAndInterests(){} //calculate interests/fees
//
//    public void printUpdatedBalances(){} //apply the interests/fees


    public static void main(String[] args) {
        // Create an AccountDatabase instance
        AccountDatabase accountDatabase = new AccountDatabase();

        // Create some sample accounts
        Account account1 = new Savings(new Profile("Seif", "Mamdouh", "10/12/2002"), 1000.0, true);
        Account account2 = new MoneyMarket(new Profile("Mikey", "Muzafarov", "1/1/2002"), 2000.0, false);

        // Test the open method to add accounts
        accountDatabase.open(account1);
        accountDatabase.open(account2);

        // Check if accounts were added
        System.out.println("Added Account 1: "  + accountDatabase.contains(account1));
        System.out.println("Added Account 2 : " + accountDatabase.contains(account2));

        //Test to remove account;
        accountDatabase.close(account1);

        //Check if the account is removed
        System.out.println("Is Account1 Present in the Array? " + accountDatabase.contains(account1));


        //Check the number of accounts in the DB
        System.out.println("The number of amount of Accounts in the Database is: " + accountDatabase.getNumAccounts());


    }

}

