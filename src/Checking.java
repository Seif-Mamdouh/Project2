/**
 * Represents a checking account
 *
 * @author Michael Muzafarov
 */
public class Checking extends Account {

    private static final double MONTHLY_FEE = 12;
    protected static final double ANNUAL_INTEREST_RATE = 0.01;

    private static final double MIN_BALANCE_TO_AVOID_MONTHLY_FEE = 1000;

    /**
     * Create checking account using a profile and starting balance
     *
     * @param profileHolder owner of the account
     * @param balance       starting balance
     */
    public Checking(Profile profileHolder, double balance) {
        super(profileHolder, balance);
    }

    /**
     * Calculate monthly interest the account will accrue
     *
     * @return monthly interest that will be accrued by the account
     */
    @Override
    public double monthlyInterest() {
        return this.balance * Checking.ANNUAL_INTEREST_RATE /
               Account.MONTHS_IN_YEAR;
    }

    /**
     * Calculate the monthly fee the account will incur.
     * Fee is MONTHLY_FEE if balance is less than
     * MIN_BALANCE_TO_AVOID_MONTHLY_FEE,
     * 0 otherwise
     *
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        if (balance >= Checking.MIN_BALANCE_TO_AVOID_MONTHLY_FEE) {
            return 0;
        }
        return Checking.MONTHLY_FEE;
    }


    /**
     * Returns the account type as a string
     * @return the account type as a string
     */
    @Override
    public String getAccountType() {
        return "Checking";
    }



}
