/**
 * Representation of a money market bank account.
 *
 * @author Seifeldeen Mohamed
 */
public class MoneyMarket extends Savings {
    private int withdrawals;

    // Constants for interest rate and fee
    private static final double ANNUAL_INTEREST_RATE = 0.045;
    private static final double ANNUAL_LOYALTY_INTEREST_RATE = 0.0475;
    private static final double MAX_AMOUNT_OF_WITHDRAWALS = 3;
    //withdrawal
    private static final double WITHDRAWAL_FEE = 10;
    protected static final double MIN_AMOUNT = 2000.0;
    private static final double NO_FEE = 0;
    private static final double MONTHLY_FEE = 25;


    /**
     * Construct money market with the account holder, starting balance,
     * and loyalty status
     *
     * @param holder account owner's profile
     * @param balance starting balance
     * @param isLoyal loyalty status
     */
    public MoneyMarket(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance, isLoyal);
        this.withdrawals = 0;
    }

    /**
     * Most typically used Money Market constructor, bc has default loyal
     *
     * @param holder owner of account
     * @param balance starting balance
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, true);
        this.withdrawals = 0;
    }

    /**
     * Method that overrides the method in the savings class
     * Calculate monthly interest based on the annual interest rate
     *
     * @return monthly interest
     */
    @Override
    public double monthlyInterest() {
        double interestRate =
                this.isLoyal ?
                ANNUAL_LOYALTY_INTEREST_RATE :
                ANNUAL_INTEREST_RATE;

        return balance * interestRate / Account.MONTHS_IN_YEAR;
    }

    /**
     * Method to check if the balance is $2000 or more for monthly fees
     *
     * @return monthly fee to be charged
     */
    @Override
    public double monthlyFee() {
        double fee = NO_FEE;
        if (balance < MIN_AMOUNT) {
            fee += MONTHLY_FEE;
        }

        if(this.withdrawals > MAX_AMOUNT_OF_WITHDRAWALS){
            fee += WITHDRAWAL_FEE;
        }
        return fee;
    }

    /**
     * Method to check and update the loyalStatus based on the balance
     */
    public void updateLoyalStatus() {
        isLoyal = balance >= MIN_AMOUNT;
    }


    /**
     * method to make a withDrawl
     *
     * @param amount amount to withdraw
     */
    @Override
    public void makeWithdrawal(double amount) {
        super.makeWithdrawal(amount);
        withdrawals++;
        if(this.balance < MIN_AMOUNT){
            this.isLoyal = false;
        }
    }

    /**
     * Deposit to account
     *
     * @param amount the amount of money to deposit
     */
    @Override
    public void makeDeposit(double amount) {
        super.makeDeposit(amount);
        if(this.balance >= MIN_AMOUNT){
            this.isLoyal = true;
        }
    }

    /**
     * Change the balance and reset the withdrawals
     * @param newBalance new balance of the account
     */
    @Override
    public void updateBalance(double newBalance) {
        super.updateBalance(newBalance);
        this.withdrawals = 0;
    }

    /**
     * Return the account type as a string
     *
     * @return account type string
     */
    @Override
    public String getAccountType() {
        return "MoneyMarket";
    }

    /**
     * Some test cases for money market
     *
     * @param args does not take command line args
     */
    public static void main(String[] args) {

        Date dat3 = new Date(12, 12, 2002);
        // Create a Money Market account with a balance of $3000
        MoneyMarket mmAccount =
                new MoneyMarket(new Profile("Seif", "Mamdouh", dat3),
                                200.0,
                                false
                );

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

    @Override
    public String errorStringIfDoesNotMeetCreationCriteria() {
        String prior = super.errorStringIfDoesNotMeetCreationCriteria();
        if (prior != null) {
            return prior;
        }
        else if (balance < MIN_AMOUNT) {
            return String.format(
                    "Minimum of $%d to open a Money Market account.",
                    (int) MIN_AMOUNT
            );
        }
        return null;
    }

    /**
     * Override string to return Money Market::Savings as name
     *
     * @return proper string representation of a money market account
     */
    @Override
    protected String fullClassName() {
        return String.format("%s::%s",
                             super.fullClassName(),
                             Savings.class.getSimpleName()
        );
    }

    @Override
    public String toString() {
        return String.format(
                "%s::withdrawal: %d",
                super.toString(),
                this.withdrawals
        );

    }
}