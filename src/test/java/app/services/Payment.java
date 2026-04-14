package app.services;

import app.app.exceptions.DatabaseException;
import app.entities.Customer;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class Payment {
    private static ConnectionPool connectionPool = ConnectionPool.getInstance("postgres", "postgres", "jdbc:postgresql://localhost:5432/%s", "cupcake");
    private Customer testCustomer;

    //Dette gøres før hver test i denne klasse. Initialiserer testdata (testkunden fra DB)
    @BeforeEach
    void setUp() {
        testCustomer = new Customer(1, "TEST", "BRUGER", "test@test.dk", "qwerty26", "customer", 100.00);
    }

    @Test
    void isBalanceUpdatedCorrectlyInDatabase() throws DatabaseException {
        //Arrange
        double amountToPay = 30.00;
        double expectedBalance = 70.00;
        //Act
        PaymentService.Payment(testCustomer, amountToPay, connectionPool);
        //Assert
        assertEquals(expectedBalance, testCustomer.getBalance());
        //Test går igennem, men balance er nu 170 ist 30
        //Fikset, beregner rigtigt i metode Payment med -total
    }

    @Test
    void shouldThrowExceptionIfBalanceIsTooLow() {
        // 1. Arrange
        double price = 5000.00;

        // 2. Act
        try {
            PaymentService.Payment(testCustomer, price, connectionPool);

        } catch (DatabaseException e) {
            // 3. Assert
            // Er fejlbesked rigtig
            assertEquals("Ordren kan ikke gennemføres, indsæt venligst flere penge på din konto", e.getMessage());
        }
    }

    @Test
    void shouldAcceptPaymentBecauseTotalIsLessThanBalance() {
        // 1. Arrange
        double price = 20.00;

        // 2. Act
        try {
            PaymentService.Payment(testCustomer, price, connectionPool);
            assertEquals(80, testCustomer.getBalance());
            System.out.println("Test bestået, ny saldo: " + testCustomer.getBalance());

        } catch (DatabaseException e) {
            // 3. Assert
            // Er fejlbesked rigtig
            fail("Betaling fejlede, selvom kunden havde penge nok på konto");
        }
    }
}

