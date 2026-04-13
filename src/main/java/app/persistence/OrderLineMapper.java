package app.persistence;

import app.app.exceptions.DatabaseException;
import app.entities.Bottom;
import app.entities.Cupcake;
import app.entities.OrderLine;
import app.entities.Topping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderLineMapper {
    //CRUD - Create, Read, Update, Delete.

    //Skal der være en updateOrderLine() ?????

    //Sender OrderLine-objekter videre til databasen.
    public static void createOrderLine(OrderLine orderLine, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into order_lines (order_id, topping_id, bottom_id, quantity, unit_price, discount) values (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, orderLine.getOrderId());
            ps.setInt(2, orderLine.getCupcake().getTopping().getComponentId());
            ps.setInt(3, orderLine.getCupcake().getBottom().getComponentId());
            ps.setInt(4, orderLine.getQuantity());
            ps.setDouble(5, orderLine.getUnitPrice());
            ps.setInt(6, orderLine.getDiscount());

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

                Topping topping = CupcakeComponentMapper.getToppingById(toppingId, connectionPool);
                Bottom bottom = CupcakeComponentMapper.getBottomById(bottomId, connectionPool);
                Cupcake cupcake = new Cupcake(topping, bottom);

                orderLines.add(new OrderLine(orderLineId, orderId, cupcake, quantity, unitPrice, discount));
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
