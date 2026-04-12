package app.entities;

public class Admin extends User{


    public Admin(int user_id, String firstName, String lastName, String email, String password, String role) {
        super(user_id, firstName, lastName, email, password, role);
    }
}
