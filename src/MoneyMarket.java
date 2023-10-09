public class MoneyMarket extends Savings {
    private int withdrawals;

    // Constants for interest rate and fee
    private static final double ANNUAL_INTEREST_RATE = 0.0475;
    private static final double MAX_AMOUNT_OF_WITHDRAWLS = 3;
    private static final double WITHDRAWL_FEE = 10;
    private static final double MIN_AMOUNT = 2000.0;


    /**
     * default constructor for MoneyMarket class
     * @param holder
     * @param balance
     * @param isLoyal
     */
    public MoneyMarket(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance, isLoyal);
        this.withdrawals = 0;
    }


    /**
     * Method that overrides the method in the savings class
     * Calculate monthly interest based on the annual interest rate
     * @return
     */
    @Override
    public double monthlyInterest() {
        return balance * ANNUAL_INTEREST_RATE / 12;
    }

    /**
     * Method to check if the balance is $2000 or more for monthly fees
     * @return
     */
    @Override
    public double monthlyFee() {
        if (balance >= MIN_AMOUNT) {
            return NO_FEE;
        }
        return MONTHLY_FEE;
    }

    /**
     * Method to check and update the loyalStatus based on the balance
     */
    public void updateLoyalStatus() {
        isLoyal = balance >= MIN_AMOUNT;
    }

    /**
     * method to make a withDrawl
     * @param amount
     */
    public void makeWithdrawal(double amount) {
        if (amount > 0) {
            withdrawals++;
            if (withdrawals > MAX_AMOUNT_OF_WITHDRAWLS) {
                balance -= WITHDRAWL_FEE;
            }
        }
    }

    public static void main(String[] args) {
        // Create a Money Market account with a balance of $3000
        MoneyMarket mmAccount = new MoneyMarket(new Profile("Seif", "Mamdouh", "10/12/2002"), 200.0, false);

        // Perform withdrawals
        mmAccount.makeWithdrawal(100); // 1st withdrawal
        mmAccount.makeWithdrawal(50); // 2nd withdrawal

        // Check and update loyal status based on balance
        mmAccount.updateLoyalStatus();

        // Calculate and display monthly interest and fees
        double monthlyInterest = mmAccount.monthlyInterest();
        double monthlyFee = mmAccount.monthlyFee();

        System.out.println("Monthly Interest: $" + monthlyInterest);
        System.out.println("Monthly Fee: $" + monthlyFee);

    }
}