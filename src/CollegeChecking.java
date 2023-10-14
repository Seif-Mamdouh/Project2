/**
 * Represents a college checking account
 *
 * @author Michael Muzafarov
 */
public class CollegeChecking extends Checking {

    private Campus campus;

    /**
     * Construct college checking account with a profile, initial balance,
     * and campus
     *
     * @param profileHolder owner of account
     * @param balance       initial balance
     * @param campus        which Rutgers campus the student attends
     */
    public CollegeChecking(
            Profile profileHolder,
            double balance,
            Campus campus
    ) {
        super(profileHolder, balance);
        this.campus = campus;
    }

    /**
     * get campus associated with this college checking account
     *
     * @return campus
     */
    public Campus getCampus() {
        return campus;
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

    @Override
    public String toString() {
        return String.format("%s::%s", super.toString(), this.campus);
    }

}
