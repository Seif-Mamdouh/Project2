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
    private static final int[] CAMPUS_CODES = {
            0, 1, 2
    };

    /**
     * Creates a transaction manager with an AccountDatabase
     */
    public TransactionManager() {
        this.accountDatabase = new AccountDatabase();
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
     * Print the account info and an additional string
     */
    private static void printAccountAnnouncement(
            Account account, String announcement
    ) {
        String acronym = "A";
        if (account instanceof CollegeChecking) {
            acronym = "CC";
        }
        else if (account instanceof Checking) {
            acronym = "C";
        }
        else if (account instanceof MoneyMarket) {
            acronym = "MM";
        }
        else if (account instanceof Savings) {
            acronym = "S";
        }
        String message = String.format("%s(%s) %s.",
                                       account.profileHolder.toString(),
                                       acronym,
                                       announcement
        );

        System.out.println(message);

    }


    /**
     * Will print "Not a valid amount"
     */
    private static void printNotValidAmount() {
        System.out.println("Not a valid amount.");
    }

    /**
     * Will print "Initial deposit cannot be 0 or negative."
     */
    private static void printInitialDepositCannotBeZeroOrNegative() {
        System.out.println("Initial deposit cannot be 0 or negative.");
    }

    /**
     * Prints error if number of tokens do not match desired command
     *
     * @param tokens array of space delimited user inputs
     * @return true if there was an error, or false otherwise
     */
    private static boolean checkLengthAndPrintError(
            String[] tokens
    ) {
        String commandToken = tokens[COMMAND_TYPE_INDEX];

        String baseError = "Missing data for %s an account.\n";
        String reason = null;

        if (commandToken.equals("C")) {
            if (tokens.length <= DATE_INDEX) {
                reason = "closing";
            }
            else {
                return false;
            }
        }
        else if (tokens.length > MONEY_AMOUNT_INDEX) {
            return false;
        }
        else if (commandToken.equals("O")) {
            reason = "opening";
        }
        else if (commandToken.equals("D")) {
            reason = "deposit";
        }
        else if (commandToken.equals("W")) {
            reason = "withdraw";
        }
        else {
            System.out.printf("Unrecognized Command: '%s' \n", commandToken);
        }
        System.out.printf(baseError, reason);
        return true;
    }

    /**
     * Parses a string containing a double with at most 2 decimal places
     *
     * @param moneyAmountString the string containing the alleged double
     * @return the Double object or null if it was invalid
     */
    private static Double parseMoneyAmount(String moneyAmountString) {

        int startingIndex = 0;
        if (moneyAmountString.charAt(0) == '-') {
            startingIndex = 1;
        }

        boolean haveSeenDecimal = false;
        for (int i = startingIndex; i < moneyAmountString.length(); i++) {
            if (moneyAmountString.charAt(i) == '.') {
                if (haveSeenDecimal) {
                    TransactionManager.printNotValidAmount();
                    return null;
                }
                else if (moneyAmountString.length() - 1 - i > 2) {
                    TransactionManager.printNotValidAmount();
                }
                haveSeenDecimal = true;
            }
            else if (!Character.isDigit(moneyAmountString.charAt(i))) {
                TransactionManager.printNotValidAmount();
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
        else if (accountTypeString.equals("MM")) {
            return new MoneyMarket(profileHolder, balance);
        }


        if (accountTypeString.equals("S")) {
            boolean loyalty;
            if (additionalInfoString == null) {
                System.out.println("no additional info");
                return null;
            }
            else if (additionalInfoString.equals("1")) {
                loyalty = true;
            }
            else if (additionalInfoString.equals("0")) {
                loyalty = false;
            }
            else {
                System.out.println("loyalty is either 1 or 0");
                return null;
            }
            return new Savings(profileHolder, balance, loyalty);
        }

        else if (accountTypeString.equals("CC")) {
            Campus campus = null;
            for (Campus curr : Campus.values()) {
                if (String.valueOf(curr.CAMPUS_CODE)
                          .equals(additionalInfoString)) {
                    campus = curr;
                }
            }
            return new CollegeChecking(profileHolder, balance, campus);
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

        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        String criteriaErrorString = profile.errorStringIfDoesNotmeetCreationCriteria();
        if(criteriaErrorString != null){
            System.out.println(criteriaErrorString);
            return null;
        }

        Double moneyAmount = 0.0;
        if (tokens.length > MONEY_AMOUNT_INDEX) {
            moneyAmount =
                    TransactionManager.parseMoneyAmount(tokens[MONEY_AMOUNT_INDEX]);
        }

        if (moneyAmount == null) {
            return null;
        }

        String additionalInfo = null;
        if (tokens.length > ADDITIONAL_INFO_INDEX) {
            additionalInfo = tokens[ADDITIONAL_INFO_INDEX];
        }



        return openProperAccount(accountType,
                                 profile,
                                 moneyAmount,
                                 additionalInfo
        );
    }

    /**
     * Will run the supplied method if the database is not empty, otherwise
     * will print that the database is empty
     *
     * @param method method to execute if database is not empty
     */
    private void runMethodIfDatabaseNotEmpty(
            Runnable method, String listMessage
    ) {
        if (this.accountDatabase.getNumAccounts() == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        System.out.println("\n" + listMessage);
        method.run();
        System.out.println("*end of list.\n");

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
                this.runMethodIfDatabaseNotEmpty(
                        this.accountDatabase::printSorted,
                        "*Accounts sorted by account type and profile."
                );
                return wasHandled;
            case "PI":
                this.runMethodIfDatabaseNotEmpty(
                        this.accountDatabase::printFeesAndInterests,
                        "*list of accounts with fee and monthly interest"
                );
                return wasHandled;
            case "UB":
                this.runMethodIfDatabaseNotEmpty(
                        this.accountDatabase::printUpdatedBalances,
                        "*list of accounts with fees and interests applied."
                );
                return wasHandled;
        }
        return !wasHandled;

    }

    private boolean checkForExistence(Account account){
        boolean normalContains = this.accountDatabase.contains(account);
        if(normalContains || !(account instanceof Checking)){
            return normalContains;
        }

        Account opposite = new CollegeChecking(account.profileHolder, 0, Campus.NEW_BRUNSWICK);
        if(account instanceof CollegeChecking){
            opposite = new Checking(account.profileHolder, 0);
        }
        return this.accountDatabase.contains(opposite);

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

        else if (checkLengthAndPrintError(tokens)) {
            return;
        }

        Account account = aggregateAndCreateAccount(tokens);
        if (account == null) {
            return;
        }
        else if (commandType.equals("O")) {
            String criteriaErrorString = account.errorStringIfDoesNotmeetCreationCriteria();
            if(criteriaErrorString != null){
                System.out.println(criteriaErrorString);
            }
            else if (this.checkForExistence(account)) {
                TransactionManager.printAccountAnnouncement(account,
                                                            "is already in " +
                                                            "the database"
                );
            }
            else {
                TransactionManager.printAccountAnnouncement(account, "opened");
                this.accountDatabase.open(account);
            }
            return;
        }
        else if (!this.accountDatabase.contains(account)) {
            TransactionManager.printAccountAnnouncement(account,
                                                        "is not in the database"
            );
            return;
        }

        else if (commandType.equals("C")) {
            TransactionManager.printAccountAnnouncement(account,
                                                        "has been closed"
            );
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
        System.out.println("Transaction Manager is running.\n");
        Scanner in = new Scanner(System.in);
        int lineNumber = 0;
        while (in.hasNext()) {
            lineNumber++;
            String userInput = in.nextLine().trim();
            if (userInput.isEmpty()) {
                continue;
            }

            String[] tokens = userInput.split("\\s+");

            if (tokens[COMMAND_TYPE_INDEX].equals("Q")) {
                break;
            }
            else if (!validCommand(tokens[COMMAND_TYPE_INDEX])) {
                System.out.println("Invalid command!");
                continue;
            }
            else if (tokens.length > MAX_COMMAND_TOKENS) {
                System.out.println("Too many tokens");
                continue;
            }

            this.handleCommands(tokens);
        }
        in.close();
    }

}
