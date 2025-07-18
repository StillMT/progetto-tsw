package it.unisa.tsw_proj.model.bean;

public class OrderItemBean {

    // Costruttore
    public OrderItemBean(ProductBean product, double price, int qt) {
        this.product = product;
        this.price = price;
        this.qt = qt;
    }

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public ProductBean getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    public int getQt() {
        return qt;
    }

    public double getTotal() {
        return price * qt;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }

    // Attributi
    private int id;
    private int orderId;
    private ProductBean product;
    private double price;
    private int qt;
}
