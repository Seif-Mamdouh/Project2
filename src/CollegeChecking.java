/**
 * Represents a college checking account
 *
 * @author Michael Muzafarov
 */
public class CollegeChecking extends Checking {

    private final Campus campus;

    private static final int INELIGIBLE_AGE = 24;

    /**
     * Construct college checking account with a profile, initial balance,
     * and campus
     *
     * @param profileHolder owner of account
     * @param balance       initial balance
     * @param campus        which Rutgers campus the student attends
     */
    public CollegeChecking(
            Profile profileHolder, double balance, Campus campus
    ) {
        super(profileHolder, balance);
        this.campus = campus;
    }

    /**
     * There is no monthly fee for college checking
     *
     * @return 0
     */
    @Override
    public double monthlyFee() {
        return 0;
    }


    @Override
    public String getAccountType() {
        return "CollegeChecking";
    }

    /**
     * Get the appropriate error message to display to user if this account
     * does not fit opening criteria
     *
     * @return the error message or null if the account can be opened
     */
    @Override
    public String errorStringIfDoesNotMeetCreationCriteria() {
        String prior = super.errorStringIfDoesNotMeetCreationCriteria();
        if (prior != null) {
            return prior;
        }
        else if (this.getProfileType().getDateOfBirth().getAge() >=
                 INELIGIBLE_AGE) {
            return this.profileHolder.ageErrorString(
                    "over " + INELIGIBLE_AGE + ".");
        }
        else if (this.campus == null) {
            return "Invalid campus code.";
        }
        return null;
    }

    /**
     * String format of the college checking
     * @return formatted string
     */
    @Override
    public String toString() {
        return String.format("%s::%s", super.toString(), this.campus);
    }

}
