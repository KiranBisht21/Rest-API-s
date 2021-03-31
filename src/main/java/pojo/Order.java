package pojo;
public class Order {
    int orderId;
    String name;
    int quantity;
    public Order(int orderId,String name,int quantity)
    {
        this.orderId=orderId;
        this.name=name;
        this.quantity=quantity;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
