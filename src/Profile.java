/**
 * Profile represents a person, used to identify account holder.
 *
 * @author Seifeldeen Mohamed
 */
public class Profile implements Comparable<Profile> {
    //first name
    private final String fname;
    //last name
    private final  String lname;
    //date of birth
    private final Date dob;


    /**
     * Construct a profile that belongs to a person
     *
     * @param first_name first name of profile owner
     * @param last_name last name of profile owner
     * @param dateOfBirth date of birth of owner
     */
    public Profile(String first_name, String last_name, Date dateOfBirth) {
        this.fname = first_name;
        this.lname = last_name;
        this.dob = dateOfBirth;
    }

    /**
     * Getter for the first name
     *
     * @return first name
     */
    public String getFirstName() {
        return fname;
    }

    /**
     * Getter for the last name
     *
     * @return last name
     */
    public String getLastName() {
        return lname;
    }

    /**
     * Getter for date of birth
     *
     * @return date of birth
     */
    public Date getDateOfBirth() {
        return dob;
    }

    /**
     * Represent this profile as a string
     *
     * @return profile as string
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", this.fname, this.lname, this.dob);
    }

    /**
     * Return error string if this profile does not meet creation criteria
     *
     * @return error string if it does not meet creation criteria,
     * null otherwise
     */
    public String errorStringIfDoesNotMeetCreationCriteria(){
        String dateOfBirthError = null;
        if (!this.dob.isValid()) {
            dateOfBirthError = "not a valid calendar date!";
        }
        else if (this.dob.isFutureDate()) {
            dateOfBirthError = "cannot be today or a future day.";

        }
        else if (this.dob.getAge() < 16) {
            dateOfBirthError = "under 16.";
        }

        if (dateOfBirthError != null) {
            return this.ageErrorString(dateOfBirthError);
        }

        return null;
    }


    /**
     * Method to compare profiles
     *
     * @param otherProfile the object to be compared.
     * @return the int describing the two profile's relation as per compareTo
     */
    @Override
    public int compareTo(Profile otherProfile) {
        int comparingLastName =
                String.CASE_INSENSITIVE_ORDER.compare(this.getLastName(),
                                                      otherProfile.getLastName()
                );

        if (comparingLastName != 0) {
            return comparingLastName;
        }

        int comparingFirstName =
                String.CASE_INSENSITIVE_ORDER.compare(this.getFirstName(),
                                                      otherProfile.getFirstName()
                );

        if (comparingFirstName != 0) {
            return comparingFirstName;
        }

        int comparingDateOfBirth =
                this.dob.compareTo(otherProfile.getDateOfBirth());

        return comparingDateOfBirth;
    }

    /**
     * Checks if two profiles are the same
     *
     * @param other other object to check equality with
     * @return true if the profiles match, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Profile otherProfile)) {
            return false;
        }
        return this.compareTo(otherProfile) == 0;
    }

    /**
     * Print the age info and an additional string talking about why it isn't
     * allowed
     *
     * @param ageErrorMessage the error to print about the age
     * @return error string
     */
    public String ageErrorString(String ageErrorMessage) {

        return String.format("DOB invalid: %s %s",
                             this.getDateOfBirth().toString(),
                             ageErrorMessage
        );

    }

}

