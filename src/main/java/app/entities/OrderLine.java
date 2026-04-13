package app.entities;

public class OrderLine {

    private int orderLineId;
    private int orderId;
    private Cupcake cupcake;
    private int quantity;
    private double unitPrice;
    private int discount;

    public OrderLine(int orderLineId, int orderId, Cupcake cupcake, int quantity, double unitPrice, int discount) {
        this.orderLineId = orderLineId;
        this.orderId = orderId;
        this.cupcake = cupcake;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discount = discount;
    }

    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Cupcake getCupcake() {
        return cupcake;
    }

    public void setCupcake(Cupcake cupcake) {
        this.cupcake = cupcake;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
