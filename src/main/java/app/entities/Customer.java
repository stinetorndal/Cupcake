package app.entities;

public class Customer extends User{
    private double balance;

    public Customer(int user_id, String firstName, String lastName, String email, String password, String role, double balance) {
        super(user_id, firstName, lastName, email, password, role);
        this.balance = balance;
           }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return "Customer{" +
                "balance=" + balance +
                '}';
    }
}
