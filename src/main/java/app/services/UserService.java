package app.services;

import app.app.exceptions.DatabaseException;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;

public class UserService {

    //Denne metode checker om brugerdata er ok, før det sendes til DB
    public static void createCustomer(User user, ConnectionPool connectionPool) throws DatabaseException {
        String emailError = Validator.validateEmail(user.getEmail());
        if (emailError != null) {
            throw new DatabaseException(emailError);
        }
        String passwordError = Validator.validatePassword(user.getPassword());
        if (passwordError != null) {
            throw new DatabaseException(passwordError);
        }
        //Og hvis alt er ok:
        UserMapper.createUser(user, connectionPool);
    }
}
