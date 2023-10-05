public class Savings extends Account {
    protected boolean isLoyal;

    // Constants for interest rate and fee
    private static final double DEFAULT_INTEREST_RATE = 0.02; // 2% interest rate
    private static final double LOYALTY_INTEREST_RATE = 0.03; // 3% interest rate for loyal customers
    private static final double MONTHLY_FEE = 2.00;

    /**
     * Contrustor with default values
     *
     * @param profileHolder
     * @param balance
     * @param isLoyal
     */
    public Savings(Profile profileHolder, double balance, boolean isLoyal) {
        super(profileHolder, balance);
    }

    public boolean isLoyal() {
        return isLoyal;
    }

    @Override
    public double monthlyInterest() {
        //implement logic
        return 0.0;
    }

    @Override
    public double monthlyFee() {
        //implement logic
        return 0.0;
    }

    @Override
    public int compareTo(Account OtherAccount) {
        return 0;
    }
}
