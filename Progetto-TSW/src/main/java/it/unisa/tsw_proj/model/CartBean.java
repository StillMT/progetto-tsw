package it.unisa.tsw_proj.model;

import java.util.ArrayList;

public class CartBean {

    // Metodi di accesso
    public int getIdUser() {
        return id_user;
    }

    public ArrayList<Integer> getIdProducts() {
        return id_products;
    }

    public ArrayList<Integer> getProductsQty() {
        return productsQty;
    }

    // Metodi modificatori
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public void addProduct(int id_product, int qty) {
        id_products.add(id_product);
        productsQty.add(qty);
    }

    public void removeProduct(int id_product) {
        int index = id_products.indexOf(id_product);
        id_products.remove(index);
        productsQty.remove(index);
    }

    public void addQty(int id_product) {
        productsQty.set(id_product, productsQty.get(id_product) + 1);
    }

    public void removeQty(int id_product) {
        int prodQty = productsQty.get(id_product);
        if (prodQty > 1)
            productsQty.set(id_product, prodQty - 1);
        else
            removeProduct(id_product);
    }

    // Attributi
    private int id_user;
    final private ArrayList<Integer> id_products = new ArrayList<>();
    final private ArrayList<Integer> productsQty = new ArrayList<>();
}
