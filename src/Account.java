/**
 * Public Abstract class for all Account Types
 * @author Seifeldeen Mohamed
 */
public abstract class Account implements Comparable <Account> {
    protected Profile profileHolder;
    protected double balance;
    protected static final int MONTHS_IN_YEAR = 12;
    

    /**
     * Contrustor with default values
     * @param profileHolder
     * @param balance
     */
    public Account(Profile profileHolder, double balance){
        this.profileHolder = profileHolder;
        this.balance = balance;
    }


    /**
     * Abstract Method for Monthly Interests
     * @return
     */
    public abstract double monthlyInterest();

    /**
     * Abstract Method for Monthly fees
     * @return
     */
    public abstract double monthlyFee();

    /**
     * Abstract Method to getAccountType
     */
    public abstract String getAccountType();


    /**
     * Method to get the Profile Holder
     * @return profileHolder
     */
    public Profile getProfileType(){
        return profileHolder;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double newBalance) {
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
        return this.getAccountType().compareTo(otherAccount.getAccountType());
    }

    /**
     * Abstract Method to make a deposit for all account types
     * @param amount
     */
    public void makeDeposit(double amount) {
        if (amount > 0) {
            balance += amount; // Add the deposit amount to the balance
        }
    }

    /**
     * Method to make a withdrawal from the account.
     * @param amount The amount to withdraw.
     */
    public void makeWithdrawal(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        }
    }



}
