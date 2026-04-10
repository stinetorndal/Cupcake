package app.persistence;

import app.app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderLineMapper {
    //CRUD - Create, Read, Update, Delete.

    //Skal der være en updateOrderLine() ?????
    //Jeg tror ikke, alle metoderne kommer til at passe sammen med vores entiteter...

    //TODO jeg tror, der mangler noget i denne her metode. Jeg er ikke sikker. Men vær lige obs på, om den får fat i de rigtige oplysninger. Og skal den egentlig sende flere oplysninger med?
    //Sender OrderLine-objekter videre til databasen.
    public static void createOrderLine(int toppingId, int bottomId, int quantity, double unitPrice, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into order_lines (topping_id, bottom_id, quantity, unit_price) values (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, toppingId);
            ps.setInt(2, bottomId);
            ps.setInt(3, quantity);
            ps.setDouble(4, unitPrice);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("FEJL! Ordrelinjen blev ikke oprettet.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("FEJL! Kunne ikke oprette ordrelinje. " + e.getMessage());
        }

    }

    //Henter ordrelinjer (hvor skal de bruges?)
    public static List<OrderLine> getOrderLinesByOrderNumber(int orderNumber, ConnectionPool connectionPool) throws DatabaseException {
        List<OrderLine> orderLines = new ArrayList<>();

        String sql = "select * from order_lines where order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, orderNumber);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderLineId = rs.getInt("line_id");
                int orderId = rs.getInt("order_id");
                int toppingId = rs.getInt("topping_id");
                int bottomId = rs.getInt("bottom_id");
                int quantity = rs.getInt("quantity");
                double unitPrice = rs.getDouble("unit_price");
                int discount = rs.getInt("discount");
                orderLines.add(new OrderLine(orderLineId, orderId, toppingId, bottomId, quantity, unitPrice, discount));
            }
        } catch (SQLException e) {
            throw new DatabaseException("FEJL! Kunne ikke hente ordrelinjer. " + e.getMessage());
        }
        return orderLines;
    }

    //Sletter en given ordrelinje.
    public static void deleteOrderLine(int orderLineId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from order_lines where line_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, orderLineId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("FEJL! Kunne ikke finde ordrelinje.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("FEJL! Kunne ikke slette ordrelinje. " + e.getMessage());
        }
    }
}
