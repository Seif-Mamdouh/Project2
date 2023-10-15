/**
 * Savings Class extending Account Class
 *
 * @author Seifeldeen Mohamed
 */
public class Savings extends Account {
    protected boolean isLoyal;
    // Constants for interest rate and fee
    private static final double DEFAULT_INTEREST_RATE = 0.04;
    private static final double LOYALTY_INTEREST_RATE = 0.0425;
    // 4.25% interest rate for loyal customers
    private static final double MINIMUM_BALANCE_FOR_NO_FEE = 500.00;
    protected static final double MONTHLY_FEE = 25.00;
    protected static final double NO_FEE = 0.0;

    /**
     * Construct savings account
     *
     * @param profileHolder owner of account's profile
     * @param balance starting balance of account
     * @param isLoyal loyalty status of account
     */
    public Savings(Profile profileHolder, double balance, boolean isLoyal) {
        super(profileHolder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * method to calculate the monthly interest
     *
     * @return the monthly interest
     */
    @Override
    public double monthlyInterest() {
        // if the customer is loyal, then they get the loyal interest rate
        // otherwise give them the default
        double interestRate =
                isLoyal ? LOYALTY_INTEREST_RATE : DEFAULT_INTEREST_RATE;
        return balance * interestRate / Account.MONTHS_IN_YEAR;
    }

    /**
     * Calculate the monthly fee, no fee if above minimum threshold,
     * and standard fee otherwise
     *
     * @return monthly fee
     */
    @Override
    public double monthlyFee() {
        if (balance >= MINIMUM_BALANCE_FOR_NO_FEE) {
            return NO_FEE;
        }
        return MONTHLY_FEE;
    }

    /**
     * Return the account type as a string
     *
     * @return account type as string
     */
    @Override
    public String getAccountType() {
        return "Savings";
    }

    /**
     * Return string representation of Savings
     *
     * @return string representation
     */
    @Override
    public String toString() {

        String loyaltyString = "";
        if (this.isLoyal) {
            loyaltyString = "::is loyal";
        }
        //System.out.println();
        return String.format("%s%s", super.toString(), loyaltyString);

    }
}
