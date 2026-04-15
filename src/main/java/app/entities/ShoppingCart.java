package app.entities;

import java.util.List;

public class ShoppingCart {
    private List<OrderLine> orderlines;

    public ShoppingCart(List<OrderLine> orderlines) {
        this.orderlines = orderlines;
    }

    public List<OrderLine> getOrderLines() { return orderlines; }

    //Udregner kurvens totalpris (hver ordrelinje lagt sammen)
    public double totalPrice() {
        double totalPrice = 0;

        for (OrderLine orderLine : orderlines) {
            totalPrice += orderLine.getOrderLinePrice();
        }
        return totalPrice;
    }

    public void setOrderLines(List<OrderLine> orderlines) { this.orderlines = orderlines; }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "Order lines = " + orderlines +
                '}';
    }
}
