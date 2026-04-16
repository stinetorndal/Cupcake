package app.entities;

import java.time.LocalDate;

public class Order {
    private int orderId;
    private int userId;
    private LocalDate timeStamp;
    private ShoppingCart shoppingCart;

    public Order(int orderId, int userId, LocalDate timeStamp, ShoppingCart shoppingCart) {
        this.orderId = orderId;
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.shoppingCart = shoppingCart;
    }

    public Order(int orderId, int userId, LocalDate timeStamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.timeStamp = timeStamp;

    }
    public int getOrderId() { return orderId; }

    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public LocalDate getTimeStamp() { return timeStamp; }

    public void setTimeStamp(LocalDate timeStamp) { this.timeStamp = timeStamp; }

    public ShoppingCart getShoppingCart() { return shoppingCart; }

    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart; }

    @Override
    public String toString() {
        return "Order{" +
                "order id = " + orderId +
                ", user id = " + userId +
                ", time stamp = " + timeStamp +
                ", shopping cart = " + shoppingCart +
                '}';
    }
}
