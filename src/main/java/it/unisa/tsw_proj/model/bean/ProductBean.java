package it.unisa.tsw_proj.model.bean;

import java.util.ArrayList;
import java.util.List;

public class ProductBean {

    // Costruttori
    public ProductBean() {}

    public ProductBean(int id, String brand, String model, String description, double basePrice, int idCategory) {
        this.id = id;
        this.model = model;
        this.description = description;
        this.basePrice = basePrice;
        this.idCategory = idCategory;
    }

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public List<ProductVariantBean> getProductVariants() {
        return productVariants;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setIdCategory(int id_category) {
        this.idCategory = id_category;
    }

    public void addVariant(ProductVariantBean variant) {
        productVariants.add(variant);
    }

    public void removeVariant(int id) {
        for (ProductVariantBean variant : productVariants)
            if (variant.getId() == id) {
                productVariants.remove(variant);
                break;
            }
    }

    // Attributi
    private int id;
    private String brand;
    private String model;
    private String description;
    private double basePrice;
    private int idCategory;
    private final List<ProductVariantBean> productVariants = new ArrayList<>();
}
