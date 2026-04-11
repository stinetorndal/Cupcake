package app.persistence;

import app.app.exceptions.DatabaseException;
import java.sql.*;

public class OrderMapper {

    public static int createOrder(int userId, ConnectionPool connectionPool)
            throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, status) VALUES (?, 'pending')";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new DatabaseException("Ordre kunne ikke oprettes");
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl ved oprettelse af ordre", e.getMessage());
        }
    }

    public static void deleteOrder(int orderId, ConnectionPool connectionPool)
            throws DatabaseException {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Ordren kunne ikke findes og blev ikke slettet");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl ved sletning af ordre", e.getMessage());
        }
    }

    public static void updateOrderLineQuantity(int lineId, int newQuantity, ConnectionPool connectionPool)
            throws DatabaseException {
        // Rettet: Tilføjet mellemrum mellem order_lines og SET
        String sql = "UPDATE order_lines SET quantity = ? WHERE line_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, lineId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                // Rettet: 'throw' i stedet for 'throws'
                throw new DatabaseException("Kunne ikke opdatere antallet på ordrelinje");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Databasefejl ved opdatering af antal", e.getMessage());
        }
    }
}