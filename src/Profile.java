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
        dob = dateOfBirth;
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
        return "Name: " + fname + "Last Name: " + lname + "Date of birth: " + dob;
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

}

