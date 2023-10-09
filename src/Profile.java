public class Profile implements Comparable<Profile> {
    //first name
    private String fname;
    //last name
    private String lname;
    //date of birth
    private String dob;


    /**
     * Default Constructor
     * @param first_name
     * @param last_name
     * @param dateOfBirth
     */
    public Profile(String first_name, String last_name, String dateOfBirth) {
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

    public String getDateOfBirth() {
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

    public static void main(String[] args) {
        // Create instances of Profile
        Profile profile1 = new Profile("John", "Doe", "1990-01-15");
        Profile profile2 = new Profile("John", "Doe", "1985-05-20");
        Profile profile3 = new Profile("Bob", "Johnson", "1985-03-10");

        int result1to2 = profile1.compareTo(profile2);
        int result1to3 = profile1.compareTo(profile3);

        System.out.println("Comparison result 1 to 2: " + result1to2);
        System.out.println("Comparison result 1 to 3: " + result1to3);
    }
}

