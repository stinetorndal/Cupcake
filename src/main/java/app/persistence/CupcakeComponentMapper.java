package app.persistence;

import app.app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CupcakeComponentMapper {

    public static List<Topping> getAllToppings(ConnectionPool connectionPool) throws DatabaseException {
        List<Topping> allToppings = new ArrayList<>();

        String sql = "select * from toppings order by topping_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int componentId = rs.getInt("topping_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                allToppings.add(new CupcakeComponent(componentId, name, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("FEJL! Kunne ikke finde toppings. " + e.getMessage());
        }
        return allToppings;
    }

    public static List<Bottom> getAllBottoms(ConnectionPool connectionPool) throws DatabaseException {
        List<Bottom> allBottoms = new ArrayList<>();

        String sql = "select * from bottoms order by bottom_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int componentId = rs.getInt("bottom_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                allBottoms.add(new CupcakeComponent(componentId, name, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException("FEJL! Kunne ikke finde bottoms. " + e.getMessage());
        }
        return allBottoms;
    }

    public static Topping getToppingById(int toppingId, ConnectionPool connectionPool) throws DatabaseException {
        Topping topping = null;

        String sql = "select * from toppings where topping_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, toppingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int componentId = rs.getInt("topping_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                topping = new Topping(componentId, name, price);
            }
        } catch (SQLException e) {
            throw new DatabaseException("FEJL! Kunne ikke finde den valgte topping. " + e.getMessage());
        }
        return topping;
    }

    public static Bottom getBottomById(int bottomId, ConnectionPool connectionPool) throws DatabaseException {
        Bottom bottom = null;

        String sql = "select * from bottoms where bottom_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, bottomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int componentId = rs.getInt("bottom_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                bottom = new Bottom(componentId, name, price);
            }
        } catch (SQLException e) {
            throw new DatabaseException("FEJL! Kunne ikke finde den valgte bottom. " + e.getMessage());
        }
        return bottom;
    }
}
