package it.unisa.tsw_proj.model.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductBean {

    // Costruttori
    public ProductBean() {}

    public ProductBean(int id, String brand, String model, String description, int idCategory) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.idCategory = idCategory;
    }

    public ProductBean(String brand, String model, String description, int idCategory) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.idCategory = idCategory;
    }

    public ProductBean(int id, String brand, String model, ProductVariantBean pv) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        addVariant(pv);
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

    public int getIdCategory() {
        return idCategory;
    }

    public List<ProductVariantBean> getProductVariants() {
        return productVariants;
    }

    /*public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("brand", brand);
        json.put("model", model);
        json.put("description", description);
        json.put("idCategory", idCategory);

        JSONArray variants = new JSONArray();
        for (ProductVariantBean v : productVariants) {
            JSONObject variant = new JSONObject();
            variant.put("id", v.getId());
            variant.put("id_product", v.getIdProduct());
            variant.put("hexColor", v.getHexColor());
            variant.put("storage", v.getStorage());
            variant.put("stock", v.getStock());
            variant.put("price", v.getPrice());
            variant.put("salePrice", v.getSalePrice());
            variant.put("salePercentage", v.getSalePercentage());
            variant.put("saleExpireDate", v.getSaleExpireDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            variants.put(variant);
        }
        json.put("variants", variants);

        return json;
    }*/

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
    private int idCategory;
    private final List<ProductVariantBean> productVariants = new ArrayList<>();
}
