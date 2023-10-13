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

    public TransactionManager(AccountDatabase AccountDatabase){
        this.accountDatabase = AccountDatabase;
    }

//    private static String parseDate(String dateString){
//        String[] dateTokens = dateString.split("/");
//        if(dateTokens.length != EXPECTED_NUMBER_OF_DATE_TOKENS){
//            return null;
//        }
//        if(dateTokens[MONTH_INDEX].length() < )
//
//        //int month, day, year;
//        try {
//            int month = Integer.parseInt(dateTokens[MONTH_INDEX]);
//            int day = Integer.parseInt(dateTokens[DAY_INDEX]);
//            int year = Integer.parseInt(dateTokens[YEAR_INDEX]);
//        }
//        catch (NumberFormatException exception){
//            return null;
//        }
//        if
//
//    }

    private static Account openProperAccount(String accountTypeString, Profile profileHolder, double balance, String additionalInfoString){

        if(accountTypeString.equals("C")) {
            return new Checking(profileHolder, balance);
        }
        else if(accountTypeString.equals("CC")) {
            //needs campus
            return new CollegeChecking(profileHolder, balance);
        }

        boolean loyalty;
        if(additionalInfoString.equals("1")) {
            loyalty = true;
        }
        else if(additionalInfoString.equals("0")) {
            loyalty = false;
        }
        else{
            System.out.print("loyalty is either 1 or 0");
            return null;
        }

        if(accountTypeString.equals("S")) {
            return new Savings(profileHolder, balance, loyalty);
        }
        else if(accountTypeString.equals("MM")) {
            return new MoneyMarket(profileHolder, balance, loyalty);
        }

        return null;
    }

    private static Double parseMoneyAmount(String moneyAmountString){
        boolean haveSeenDecimal = false;
        for(int i = 0; i < moneyAmountString.length(); i++){
            if(moneyAmountString.charAt(i) == '.'){
                if(haveSeenDecimal){
                    System.out.print("Cannot have 2 decimals in one String");
                    return null;
                }
                haveSeenDecimal = true;
            }
            else if(!Character.isLetter(moneyAmountString.charAt(i))){
                System.out.print("Non-numeric char detected");
                return null;
            }
        }
        return Double.parseDouble(moneyAmountString);
    }

    public boolean handlePrintCommands(String commandType){
        switch(commandType){
            case "P":
                this.accountDatabase.printSorted();
                return false;
            case "PI":
                this.accountDatabase.printFeesAndInterests();
                return false;
            case "UB":
                this.accountDatabase.printUpdatedBalances();
                return false;
        }
        return true;

    }

    public static boolean validCommand(String commandType){
        for(String validCommand : TransactionManager.VALID_COMMAND_TYPES){
            if(validCommand.equals(commandType)){
                return true;
            }
        }
        return false;
    }

    private static Account aggregateAndCreateAccount(String[] tokens){
        String accountType = tokens[ACCOUNT_TYPE_INDEX];
        String firstName = tokens[FIRST_NAME_INDEX];
        String lastName = tokens[LAST_NAME_INDEX];
        Date dateOfBirth = Date.parseDate(tokens[DATE_INDEX]);

        Double moneyAmount = TransactionManager.parseMoneyAmount(tokens[MONEY_AMOUNT_INDEX]);
        if(moneyAmount == null){
            return null;
        }

        String additionalInfo = null;
        if(tokens.length > ADDITIONAL_INFO_INDEX){
            additionalInfo = tokens[ADDITIONAL_INFO_INDEX];
        }

        Profile profile = new Profile(firstName, lastName, dateOfBirth);

        Account account = openProperAccount(accountType, profile, moneyAmount, additionalInfo);
        if(account == null){
            return null;
        }
        return account;
    }

    private void handleCommands(String[] tokens){
        //String commandType = TransactionManager.parseCommand(tokens[COMMAND_TYPE_INDEX]);
        String commandType = tokens[COMMAND_TYPE_INDEX];

        boolean shouldContinue = handlePrintCommands(commandType);
        if(!shouldContinue){
            return;
        }

        //parse account type, first name, last name, dob,

        Account account = aggregateAndCreateAccount(tokens);

        if(commandType.equals("O")){
            this.accountDatabase.open(account);
            return;
        }

        switch(commandType){
            case "C":
                this.accountDatabase.close(account);
                break;
            case "D":
                this.accountDatabase.deposit(account, moneyAmount);
                break;
            case "W":
                this.accountDatabase.withdraw(account, moneyAmount);
                break;
        }
    }

    public void run(){
        Scanner in = new Scanner(System.in);

        while(in.hasNext()){
            String userInput = in.nextLine().trim();
            if(userInput.isEmpty()){
                continue;
            }

            String[] tokens = userInput.split("\\s+");

            if(tokens[COMMAND_TYPE_INDEX] == "Q"){
                break;
            }
            else if(!validCommand(tokens[COMMAND_TYPE_INDEX])){
                System.out.print("Invalid Command Type");
                continue;
            }
            else if(tokens.length > MAX_COMMAND_TOKENS){
                System.out.print("Too many tokens");
                continue;
            }

            this.handleCommands(tokens);
        }
    }

}
