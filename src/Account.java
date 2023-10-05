/**
 * Public Abstract class for all Account Types
 * @author Seifeldeen Mohamed
 */
public abstract class Account implements Comparable <Account> {
    protected Profile profileHolder;
    protected double balance;

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
     * Abstract Method to compare Accounts
     *
     * @param OtherAccount the object to be compared.
     * @return
     */
    @Override
    public abstract int compareTo(Account OtherAccount);

}
