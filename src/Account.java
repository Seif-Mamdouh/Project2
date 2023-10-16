
/**
 * Public Abstract class for all Account Types.
 *
 * @author Seifeldeen Mohamed
 */
public abstract class Account implements Comparable<Account> {
    protected Profile profileHolder;
    protected double balance;
    protected static final int MONTHS_IN_YEAR = 12;

    /**
     * Constructor with default values
     *
     * @param profileHolder holder of account
     * @param balance starting balance of account
     */
    public Account(Profile profileHolder, double balance) {
        this.profileHolder = profileHolder;
        this.balance = balance;
    }


    /**
     * Abstract Method for Monthly Interests
     *
     * @return monthly interest
     */
    public abstract double monthlyInterest();

    /**
     * Abstract Method for Monthly fees
     *
     * @return monthly fees for the account
     */
    public abstract double monthlyFee();

    /**
     * Abstract Method to getAccountType
     *
     * @return the type of account
     */
    public abstract String getAccountType();


    /**
     * Method to get the Profile Holder
     *
     * @return profileHolder
     */
    public Profile getProfileType() {
        return profileHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double newBalance) {
        this.balance = newBalance;
    }


    /**
     * Abstract Method to compare Accounts
     *
     * @param otherAccount the object to be compared.
     * @return an integer as per compareTo standards
     */
    @Override
    public int compareTo(Account otherAccount) {
        // Compare Account based on the account type
        int comparedAccountTypes = this.getAccountType().compareTo(otherAccount.getAccountType());
        if(comparedAccountTypes != 0){
            return comparedAccountTypes;
        }
        return this.profileHolder.compareTo(otherAccount.profileHolder);
    }

    /**
     * Abstract Method to make a deposit for all account types
     *
     * @param amount the amount of money to deposit
     */
    public void makeDeposit(double amount) {
        if (amount > 0) {
            balance += amount; // Add the deposit amount to the balance
        }
    }

    /**
     * Method to make a withdrawal from the account.
     *
     * @param amount The amount to withdraw.
     */
    public void makeWithdrawal(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        }
    }

    /**
     * Checks if two accounts are equal to each other
     *
     * @param other other object to check equality to
     * @return true if the accounts are of the same child class of Account
     * and their profiles are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Account otherAccount)) {
            return false;
        }
        return this.getAccountType().equals(otherAccount.getAccountType()) &&
               this.getProfileType().equals(otherAccount.getProfileType());
    }

    /**
     * Get the proper error to tell user if this account does not fit proper
     * account opening criteria
     * @return string with error message or null if account opening
     * criteria are passed
     */
    public String errorStringIfDoesNotMeetCreationCriteria(){
        if(this.balance <= 0){
            return "Initial deposit cannot be 0 or negative.";
        }
        return null;
    }

    /**
     * Get the non-camel-cased name of the class
     *
     * @return the account's name without camel case
     */
    protected String fullClassName(){
        String className = this.getClass().getSimpleName();

        int secondCapital = className.length();
        for(int i = 1; i < className.length(); i++){
            if(Character.isUpperCase(className.charAt(i))){
                secondCapital = i;
                break;
            }
        }
        String fullClassName = className.substring(0, secondCapital);
        if(secondCapital != className.length()){
            String secondPart = className.substring(secondCapital);
            fullClassName = fullClassName + " " + secondPart;
        }
        return fullClassName;
    }

    /**
     * Return properly formatted string to represent class
     *
     * @return string representation of class
     */
    @Override
    public String toString(){
        return String.format("%s::%s::Balance $%,.2f", this.fullClassName(), this.profileHolder, this.balance);
    }
}
