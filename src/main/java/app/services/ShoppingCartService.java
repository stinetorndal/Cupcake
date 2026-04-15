package app.services;

import app.app.exceptions.DatabaseException;
import app.entities.Cupcake;
import app.entities.OrderLine;
import app.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartService {

    private List<OrderLine> orderlines = new ArrayList<>();

    //Opretter et orderline-objekt
    public OrderLine createOrderLineObject(int toppingId, int bottomId, int quantity, ConnectionPool connectionPool) throws DatabaseException {
        Cupcake cupcake = new Cupcake(CupcakeService.getToppingById(toppingId, connectionPool), CupcakeService.getBottomById(bottomId, connectionPool));
        double price = cupcake.getUnitPrice();

        return new OrderLine(cupcake, quantity, price);
    }
}
