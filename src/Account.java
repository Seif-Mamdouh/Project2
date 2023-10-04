public abstract class Account implements Comparable <Account> {
    protected Profile profileHolder;
    protected double balance;


    /**
     * Contrustor with default values
     * @param profileHolder
     * @param balance
     */
    public Account(Profile profileHolder, double balance){
        this.profileHolder = profileHolder;
        this.balance = balance;
    }

    /**
     *
     */
    public Profile getProfile(){
        return this.profileHolder;
    }

    public double getBalance(){
        return this.balance;
    }


    public abstract double monthlyInterest();
    public abstract double monthlyFee();

    @Override
    public int compareTo(Account OtherAccount){
        //implement logic here
        return 0;
    }


}
