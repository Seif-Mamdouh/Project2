public class Profile implements Comparable<Profile> {
    //first name
    private String fname;
    //last name
    private String lname;
    //date of birth
    private Date dob;


    /**
     * Default Constructor
     * @param first_name
     * @param last_name
     * @param dateOfBirth
     */
    public Profile(String first_name, String last_name, Date dateOfBirth) {
        this.fname = first_name;
        this.lname = last_name;
        this.dob = dateOfBirth;
    }

    public String getFirstName() {
        return fname;
    }

    public String getLastName(){
        return lname;
    }

    public Date getDateOfBirth() {
        return dob;
    }

    @Override
    public String toString(){
        return String.format("%s %s %s", this.fname, this.lname, this.dob);
    }


    /**
     * Method to compare profiles
     * @param otherProfile the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Profile otherProfile) {
        int comparingLastName = this.getLastName().compareTo(otherProfile.getLastName());

        if (comparingLastName == 0){
            return this.getFirstName().compareTo(otherProfile.getFirstName());
        }

        return comparingLastName;
    }

    /**
     * Checks if two profiles are the same
     * @param other other object to check equality with
     * @return true if the profiles match, false otherwise
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Profile)){
            return false;
        }
        Profile otherProfile = (Profile) other;
        return this.compareTo(otherProfile) == 0;
    }

}

