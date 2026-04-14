package app.services;

import app.app.exceptions.DatabaseException;
import app.entities.Customer;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;

public class PaymentService {

    public static void Payment(Customer customer, double total, ConnectionPool connectionPool) throws DatabaseException {
        // 1. Tjek balance (Logik fra din pseudokode)
        if (customer.getBalance() < total) {
            throw new DatabaseException("Ordren kan ikke gennemføres, indsæt venligst flere penge på din konto");
        }

        // 2. Beregn den nye balance vha. hjælpemetoden
        double newBalance = calculateNewBalance(customer.getBalance(), total);
        customer.setBalance(newBalance);
        UserMapper.updateBalance(customer.getUserId(), -total,connectionPool);


}

        private static double calculateNewBalance (double currentBalance, double amountToWithdraw)  {
        return currentBalance - amountToWithdraw;
        }

}
