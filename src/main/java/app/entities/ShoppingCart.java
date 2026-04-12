package app.entities;

import java.util.List;

public class ShoppingCart {
    private List<Orderline> Orderlines;

    public ShoppingCart(List<Orderline> orderlines) {
        Orderlines = orderlines;
    }

    public List<Orderline> getOrderlines() { return Orderlines; }

    public void setOrderlines(List<Orderline> orderlines) { Orderlines = orderlines; }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "Orderlines=" + Orderlines +
                '}';
    }
}
