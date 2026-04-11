package app.persistence;

import app.app.exceptions.DatabaseException;

import java.sql.*;

public class OrderMapper {

    public static int createOrder (int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into orders (user_id, status) values (?, 'pending')";
        // Return generated keys = nye ordre-Id genereres
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); //ID bruges i OrderLineMapper
            }
            throw new DatabaseException("Ordre kunne ikke oprettes");
        }catch (SQLException e) {
            throw new DatabaseException("Databasefejl ved oprettelse af ordre", e.getMessage());
        }

    }
}
