import java.util.Scanner;

/**
 * Command line UI that executes user's desired actions.
 *
 * @author Michael Muzafarov
 */
public class TransactionManager {

    AccountDataBase accountDataBase;

    private static final int COMMAND_TYPE_INDEX = 0;
    private static final int ACCOUNT_TYPE_INDEX = 1;
    private static final int FIRST_NAME_INDEX = 2;

    private static final int LAST_NAME_INDEX = 3;
    private static final int DATE_INDEX = 4;
    private static final int MONEY_AMOUNT_INDEX = 5;
//    private static final int MONTH_INDEX = 0;
//
//    private static final int DAY_INDEX = 1;
//    private static final int YEAR_INDEX = 2;
//    private static final int EXPECTED_NUMBER_OF_DATE_TOKENS = 0;
//
//    private static final int MIN_NUMBER_OF_DIGITS_IN_DAY_OR_MONTH = 0;

    public TransactionManager(AccountDataBase accountDataBase){
        this.accountDataBase = accountDataBase;
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

    private static String parseCommand(String commandToken){


    }

    private void handleCommands(String userInput){
        String[] tokens = userInput.split("\\s+");

        //String commandType = TransactionManager.parseCommand(tokens[COMMAND_TYPE_INDEX]);
        String commandType = tokens[COMMAND_TYPE_INDEX];

        switch(commandType){
            case "P":
                this.accountDataBase.printSorted();
                break;
            case "PI":
                this.accountDataBase.printFeesAndInterests();
                break;
            case "UB":
                this.accountDataBase.printUpdatedBalances();
                break;
            case "Q":
                System.out.println("goodbye");
                return;
                break;
        }
        //parse account type, first name, last name, dob,
        String accountType = tokens[ACCOUNT_TYPE_INDEX];
        String firstName = tokens[FIRST_NAME_INDEX];
        String lastName = tokens[LAST_NAME_INDEX];

        Date dateOfBirth = Date.parseDate(tokens[DATE_INDEX]);

        if(commandType.equals("O")){
            this.accountDataBase.open();
        }
        //parse money amount
        double moneyAmount = TransactionManager.parseMoneyAmount(tokens[MONEY_AMOUNT_INDEX]);
        switch(commandType){
            case "C":
                this.accountDataBase.close();
                break;
            case "D":
                this.accountDataBase.deposit()
                break;
            case "W":
                this.accountDataBase.withdraw();
                break;
        }
    }

    public void run(String [] args){
        Scanner in = new Scanner(System.in);
        while(in.hasNext()){
            String userInput = in.nextLine().trim();
            if(userInput.length() == 0){
                continue;
            }
            this.handleCommands(userInput);
        }
    }

}
