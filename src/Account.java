import java.text.DecimalFormat;

/**
 * Public Abstract class for all Account Types
 *
 * @author Seifeldeen Mohamed
 */
public abstract class Account implements Comparable<Account> {
    protected Profile profileHolder;
    protected double balance;
    protected static final int MONTHS_IN_YEAR = 12;

    /**
     * Contrustor with default values
     *
     * @param profileHolder
     * @param balance
     */
    public Account(Profile profileHolder, double balance) {
        this.profileHolder = profileHolder;
        this.balance = balance;
    }


    /**
     * Abstract Method for Monthly Interests
     *
     * @return
     */
    public abstract double monthlyInterest();

    /**
     * Abstract Method for Monthly fees
     *
     * @return
     */
    public abstract double monthlyFee();

    /**
     * Abstract Method to getAccountType
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
     * @return
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
     * @param amount
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
        if (!(other instanceof Account)) {
            return false;
        }
        Account otherAccount = (Account) other;
        return this.getAccountType().equals(otherAccount.getAccountType()) &&
               this.getProfileType().equals(otherAccount.getProfileType());
    }

    public String errorStringIfDoesNotmeetCreationCriteria(){
        if(this.balance <= 0){
            return "Initial deposit cannot be 0 or negative.";
        }
        return null;
    }

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
            String secondPart = className.substring(secondCapital, className.length());
            fullClassName = fullClassName + " " + secondPart;
        }
        return fullClassName;
    }

    @Override
    public String toString(){
        return String.format("%s::%s::Balance $%,.2f", this.fullClassName(), this.profileHolder, this.balance);
    }
}
