/**
 * Savings Class extending Account Class
 * @author Seifeldeen Mohamed
 */
public class Savings extends Account {
    protected boolean isLoyal;
    // Constants for interest rate and fee
    private static final double DEFAULT_INTEREST_RATE = 0.02; // 2% interest rate
    private static final double LOYALTY_INTEREST_RATE = 0.0425; // 4.25% interest rate for loyal customers
    private static final double MINIMUM_BALANCE_FOR_NO_FEE = 500.00;
    protected static final double MONTHLY_FEE = 25.00;
    protected static final double NO_FEE = 0.0;

    /**
     * Contrustor with default values
     *
     * @param profileHolder
     * @param balance
     * @param isLoyal
     */
    public Savings(Profile profileHolder, double balance, boolean isLoyal) {
        super(profileHolder, balance);
        this.isLoyal = isLoyal;
    }

    /**
     * boolean Function to check if the customer isLoyal or not
     * @return
     */
    public boolean isLoyal() {
        return isLoyal;
    }

    /**
     * method to calculate the monthly interest
     * @return
     */
    @Override
    public double monthlyInterest() {
        // if the customer is loyal, then they get the loyal interest rate otherwise give them the default
        double interestRate = isLoyal ? LOYALTY_INTEREST_RATE : DEFAULT_INTEREST_RATE;
        return balance * interestRate / Account.MONTHS_IN_YEAR;
    }

    /**
     *
     * @return
     */
    @Override
    public double monthlyFee() {
        if(balance <= MINIMUM_BALANCE_FOR_NO_FEE){
            return NO_FEE;
        }
        return MONTHLY_FEE;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public String toString(){

        String loyaltyString = "not loyal";
        if(this.isLoyal){
            loyaltyString = "is loyal";
        }
        //System.out.println();
        return String.format("%s::%s::widthdraw: %d", super.toString(), loyaltyString, 0);

    }
}
