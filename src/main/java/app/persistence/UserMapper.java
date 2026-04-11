package app.persistence;

import app.entities.User;
/*
import app.entities.Customer; // Hvis du har en Customer subklasse
import app.entities.Admin;    // Hvis du har en Admin subklasse */
import app.app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static void createUser(String firstName, String lastName, String email, String password, ConnectionPool connectionPool)
            throws DatabaseException {

        String sql = "INSERT INTO users (first_name, last_name, email, password, role, balance) VALUES (?,?, ?, ?, 'customer', 0.00)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl ved oprettelse af bruger. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    public static User login(String email, String password, ConnectionPool connectionPool)
            throws DatabaseException {

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String role = rs.getString("role");
                double balance = rs.getDouble("balance");
                return new User(id, firstName, lastName, email, password, role, balance);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved database-opslag: " + e.getMessage());
        }
    }

    // Her henter admin alle kunder
    public static List<User> getAllCustomers(ConnectionPool connectionPool)
            throws DatabaseException {
        List<User> userList = new ArrayList<>();

        // BEMÆRK! Sorteret efter last_name. Kan ændres efter behov
        String sql = "SELECT * FROM users WHERE role = 'customer' ORDER BY last_name";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                String email = rs.getString("email");
                double balance = rs.getDouble("balance");
                userList.add(new User(id, fName, lName, email, "", "customer", balance));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af kundeliste: " + e.getMessage());
        }
        return userList;
    }


    // Her opdaterer admin saldo:
    public static void updateBalance(int userId, double amount, ConnectionPool connectionPool)
            throws DatabaseException {
        String sql = "UPDATE users SET balance = balance + ? WHERE user_ud = ?";


        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Kunne ikke opdatere saldo på bruger-ID: " + userId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl ved saldoopdatering", e.getMessage());
        }
    }
}