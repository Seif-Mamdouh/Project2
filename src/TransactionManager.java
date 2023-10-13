import java.util.Scanner;

/**
 * Command line UI that executes user's desired actions.
 *
 * @author Michael Muzafarov
 */
public class TransactionManager {

    AccountDatabase accountDatabase;

    private static final int COMMAND_TYPE_INDEX = 0;
    private static final int ACCOUNT_TYPE_INDEX = 1;
    private static final int FIRST_NAME_INDEX = 2;

    private static final int LAST_NAME_INDEX = 3;
    private static final int DATE_INDEX = 4;
    private static final int MONEY_AMOUNT_INDEX = 5;
    private static final int ADDITIONAL_INFO_INDEX = 6;
    private static final int MAX_COMMAND_TOKENS = 7;
    private static final String[] VALID_COMMAND_TYPES = {
            "O", "C", "D", "W", "P", "PI", "UB", "Q"
    };

    /**
     * Creates a transaction manager with an AccountDatabase
     * @param AccountDatabase the AccountDatabase to use
     */
    public TransactionManager(AccountDatabase AccountDatabase) {
        this.accountDatabase = AccountDatabase;
    }

    /**
     * Checks if the given user command is one of the allowed ones
     *
     * @param commandType string containing the user's command
     * @return true if it is a valid command, false otherwise
     */
    public static boolean validCommand(String commandType) {
        for (String validCommand : TransactionManager.VALID_COMMAND_TYPES) {
            if (validCommand.equals(commandType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a string containing a double with at most 2 decimal places
     *
     * @param moneyAmountString the string containing the alleged double
     * @return the Double object or null if it was invalid
     */
    private static Double parseMoneyAmount(String moneyAmountString) {
        boolean haveSeenDecimal = false;
        for (int i = 0; i < moneyAmountString.length(); i++) {
            if (moneyAmountString.charAt(i) == '.') {
                if (haveSeenDecimal) {
                    System.out.println("Cannot have 2 decimals in one String");
                    return null;
                }
                else if (moneyAmountString.length() - 1 - i > 2) {
                    System.out.println(
                            "Can't have more than 2 numbers after decimal");
                }
                haveSeenDecimal = true;
            }
            else if (!Character.isLetter(moneyAmountString.charAt(i))) {
                System.out.print("Non-numeric char detected");
                return null;
            }
        }
        return Double.parseDouble(moneyAmountString);
    }

    /**
     * Opens the proper child class of Account according to given parameters
     *
     * @param accountTypeString    string which contains the user's
     *                             specification of account type
     * @param profileHolder        The profile of the user
     * @param balance              the starting balance of the account
     * @param additionalInfoString contains an int either specifying the
     *                             customer's loyalty, their campus, or null
     *                             if the info was not provided
     * @return the created Account or null if the user's input was invalid
     */
    private static Account openProperAccount(
            String accountTypeString,
            Profile profileHolder,
            double balance,
            String additionalInfoString
    ) {

        if (accountTypeString.equals("C")) {
            return new Checking(profileHolder, balance);
        }
        else if (accountTypeString.equals("CC")) {
            //needs campus
            return new CollegeChecking(profileHolder, balance);
        }

        boolean loyalty;
        if (additionalInfoString.equals("1")) {
            loyalty = true;
        }
        else if (additionalInfoString.equals("0")) {
            loyalty = false;
        }
        else {
            System.out.print("loyalty is either 1 or 0");
            return null;
        }

        if (accountTypeString.equals("S")) {
            return new Savings(profileHolder, balance, loyalty);
        }
        else if (accountTypeString.equals("MM")) {
            return new MoneyMarket(profileHolder, balance, loyalty);
        }

        return null;
    }

    /**
     * Checks user input and attempts to create an Account from the data
     * provided
     *
     * @param tokens tokens the words the user entered delimited by spaces
     * @return the account created from user's data or null if there was an
     * issue
     */
    private static Account aggregateAndCreateAccount(String[] tokens) {
        String accountType = tokens[ACCOUNT_TYPE_INDEX];
        String firstName = tokens[FIRST_NAME_INDEX];
        String lastName = tokens[LAST_NAME_INDEX];
        Date dateOfBirth = Date.parseDate(tokens[DATE_INDEX]);

        Double moneyAmount =
                TransactionManager.parseMoneyAmount(tokens[MONEY_AMOUNT_INDEX]);
        if (moneyAmount == null) {
            return null;
        }

        String additionalInfo = null;
        if (tokens.length > ADDITIONAL_INFO_INDEX) {
            additionalInfo = tokens[ADDITIONAL_INFO_INDEX];
        }

        Profile profile = new Profile(firstName, lastName, dateOfBirth);

        return openProperAccount(accountType,
                                 profile,
                                 moneyAmount,
                                 additionalInfo
        );
    }

    /**
     * Handles any commands that need to be printed out to console
     *
     * @param commandType The String consisting containing the user's desired
     *                    command
     * @return true if the caller method should keep running because the
     * command was not a print command. False if the caller method should
     * not keep running because the command was handled
     */
    public boolean handlePrintCommands(String commandType) {
        boolean wasHandled = false;
        switch (commandType) {
            case "P":
                this.accountDatabase.printSorted();
                return wasHandled;
            case "PI":
                this.accountDatabase.printFeesAndInterests();
                return wasHandled;
            case "UB":
                this.accountDatabase.printUpdatedBalances();
                return wasHandled;
        }
        return !wasHandled;

    }

    /**
     * Handles the user's commands
     *
     * @param tokens the words the user entered delimited by spaces
     */
    private void handleCommands(String[] tokens) {
        String commandType = tokens[COMMAND_TYPE_INDEX];

        boolean shouldContinue = handlePrintCommands(commandType);
        if (!shouldContinue) {
            return;
        }

        Account account = aggregateAndCreateAccount(tokens);
        if (account == null) {
            return;
        }

        if (commandType.equals("O")) {
            this.accountDatabase.open(account);
            return;
        }

        if (!this.accountDatabase.contains(account)) {
            System.out.println("Such account does not exist");
            return;
        }

        if (commandType.equals("C")) {
            this.accountDatabase.close(account);
            return;
        }

        double balanceForDepositAndWithdraw = account.getBalance();

        if (commandType.equals("D")) {
            this.accountDatabase.deposit(account, balanceForDepositAndWithdraw);
        }
        else if (commandType.equals("W")) {
            this.accountDatabase.withdraw(account,
                                          balanceForDepositAndWithdraw
            );
        }

    }

    /**
     * Run the CLI
     */
    public void run() {
        Scanner in = new Scanner(System.in);

        while (in.hasNext()) {
            String userInput = in.nextLine().trim();
            if (userInput.isEmpty()) {
                continue;
            }

            String[] tokens = userInput.split("\\s+");

            if (tokens[COMMAND_TYPE_INDEX].equals("Q")) {
                break;
            }
            else if (!validCommand(tokens[COMMAND_TYPE_INDEX])) {
                System.out.print("Invalid Command Type");
                continue;
            }
            else if (tokens.length > MAX_COMMAND_TOKENS) {
                System.out.print("Too many tokens");
                continue;
            }

            this.handleCommands(tokens);
        }
    }

}
