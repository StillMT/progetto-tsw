package it.unisa.tsw_proj.model;

public class ProductBean {

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getIdCategory() {
        return id_category;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setIdCategory(int id_category) {
        this.id_category = id_category;
    }

    // Attributi
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private int id_category;
}
