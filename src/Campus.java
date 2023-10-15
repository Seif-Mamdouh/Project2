/**
 * A class that represents Rutgers Campuses.
 *
 * @author Michael Muzafarov
 */
public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2);
    public final int CAMPUS_CODE;

    /**
     * Enum's constructor that assigns each campus a campus code
     *
     * @param campusCode campus code pertaining to that campus
     */
    Campus(int campusCode){
        this.CAMPUS_CODE = campusCode;
    }
}
