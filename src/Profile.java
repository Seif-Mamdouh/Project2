public class Profile implements Comparable<Profile> {
    private String name;
    private String address;
    private String dob;

    public Profile(String name, String address, String dateOfBirth) {
        this.name = name;
        this.address = address;
        this.dob = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dob;
    }

    @Override
    public String toString(){
        return "Name: " + name + "Address: " + address + "Date of birth: " + dob;
    }

    @Override
    public int compareTo(Profile o) {
        return 0;
    }
}
