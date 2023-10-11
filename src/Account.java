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

    // get Account Type
    public abstract String getAccountType();
    // Abstract method to get the profile of the account
    public abstract Profile getProfileType();


    /**
     * Abstract Method to compare Accounts
     *
     * @param otherAccount the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Account otherAccount) {
        // Compare Account based on the balance
        return Double.compare(this.balance, otherAccount.balance);
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
