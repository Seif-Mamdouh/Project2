public class MoneyMarket extends Savings {
    private int withdrawal;

    // Constants for interest rate and fee
    private static final double DEFAULT_INTEREST_RATE = 0.03; // 3% interest rate
    private static final double LOYALTY_INTEREST_RATE = 0.04; // 4% interest rate for loyal customers
    private static final double MONTHLY_FEE = 12.00;
    private static final int MAX_WITHDRAWALS = 6; // Maximum allowed withdrawals per month

    public MoneyMarket(Profile holder, double balance, boolean isLoyal, int withdrawal) {
        super(holder, balance, isLoyal);
        this.withdrawal = withdrawal;
    }

    public int getWithdrawal() {
        return withdrawal;
    }

    @Override
    public double monthlyInterest() {
        //logic
        return 0.0;
    }

    @Override
    public double monthlyFee() {
        // logic
        return 0.0;
    }
}
