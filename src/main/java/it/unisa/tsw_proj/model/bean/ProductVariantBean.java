package it.unisa.tsw_proj.model.bean;

import java.awt.*;
import java.time.LocalDateTime;

public class ProductVariantBean {

    // Costruttori
    public ProductVariantBean() {}

    public ProductVariantBean(int id, int idProduct, String hexColor, int storage, int stock, double price, int salePercentage, double salePrice, LocalDateTime saleExpireDate) {
        this.id = id;
        this.idProduct = idProduct;
        color = Color.decode(hexColor);
        this.storage = storage;
        this.stock = stock;
        this.price = price;
        this.salePrice = salePrice;
        this.salePercentage = salePercentage;
        this.saleExpireDate = saleExpireDate;
    }

    public ProductVariantBean(String hexColor, int storage, int stock, double price, int salePercentage, double salePrice, LocalDateTime saleExpireDate) {
        id = -1;
        color = Color.decode(hexColor);
        this.storage = storage;
        this.stock = stock;
        this.price = price;
    }

    public ProductVariantBean(int id, String hexColor, int storage) {
        this.id = id;
        color = Color.decode(hexColor);
        this.storage = storage;
    }

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getHexColor() {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public int getStorage() {
        return storage;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public int getSalePercentage() {
        return salePercentage;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public LocalDateTime getSaleExpireDate() {
        return saleExpireDate;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setIdProduct(int id_product) {
        this.idProduct = id_product;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Attributi
    private int id;
    private int idProduct;
    private Color color;
    private int storage;
    private int stock;
    private double price;
    private int salePercentage;
    private double salePrice;
    private LocalDateTime saleExpireDate;
}
