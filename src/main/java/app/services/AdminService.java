package app.services;

import app.entities.Customer;
import app.entities.Order;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import app.app.exceptions.DatabaseException;
import java.util.List;

public class AdminService {


    // Henter alle kunder
    public static List<Customer> getAllCustomers (ConnectionPool connectionPool)
            throws DatabaseException {
        return UserMapper.getAllCustomers(connectionPool);
    }

    // Henter alle order
    public static List<Order> getAllOrders (ConnectionPool connectionPool)
        throws DatabaseException {
        return UserMapper.getAllOrders(connectionPool);
    }

    public static void updateBalance (int userId, double amount, ConnectionPool connectionPool)
        throws DatabaseException {
        UserMapper.updateBalance(userId, amount, connectionPool);
    }

    public static Customer getCustomerById(int customerId, ConnectionPool connectionPool)
            throws DatabaseException {
        return UserMapper.getCustomerById(customerId, connectionPool);
    }
    public static Customer getCustomerByEmail(String email, ConnectionPool connectionPool) throws DatabaseException {
        return UserMapper.getCustomerByEmail(email, connectionPool);
    }
}
