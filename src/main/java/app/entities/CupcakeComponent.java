package app.entities;

public class CupcakeComponent {

    private int componentId;
    private String name;
    private double price;

    public CupcakeComponent(int componentId, String name, double price) {
        this.componentId = componentId;
        this.name = name;
        this.price = price;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
