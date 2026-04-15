package app.services;

import app.app.exceptions.DatabaseException;
import app.entities.Bottom;
import app.entities.Topping;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeComponentMapper;

import java.util.List;

public class CupcakeService {

    public static List<Topping> getToppings(ConnectionPool connectionPool) throws DatabaseException {
        return CupcakeComponentMapper.getAllToppings(connectionPool);
    }

    public static List<Bottom> getBottoms(ConnectionPool connectionPool) throws DatabaseException {
        return CupcakeComponentMapper.getAllBottoms(connectionPool);
    }

    public static Topping getToppingById(int toppingId, ConnectionPool connectionPool) throws DatabaseException {
        return CupcakeComponentMapper.getToppingById(toppingId, connectionPool);
    }

    public static Bottom getBottomById(int bottomId, ConnectionPool connectionPool) throws DatabaseException {
        return CupcakeComponentMapper.getBottomById(bottomId, connectionPool);
    }
}
