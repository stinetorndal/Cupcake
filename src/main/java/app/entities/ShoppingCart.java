package app.entities;

import java.util.List;

public class ShoppingCart {
    private List<OrderLine> orderlines;

    public ShoppingCart(List<OrderLine> orderlines) {
        this.orderlines = orderlines;
    }

    public List<OrderLine> getOrderLines() { return orderlines; }

    public void setOrderLines(List<OrderLine> orderlines) { this.orderlines = orderlines; }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "Order lines = " + orderlines +
                '}';
    }
}
