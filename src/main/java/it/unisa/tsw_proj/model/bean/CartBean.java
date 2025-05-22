package it.unisa.tsw_proj.model.bean;

import java.util.ArrayList;

public class CartBean {

    public CartBean() {
        products = new ArrayList<>();
    }

    // Metodi di accesso
    public int getIdUser() {
        return id_user;
    }

    public ArrayList<CartedProduct> getProductList() {
        return products;
    }

    public int getCartItemsCount() {
        return products.size();
    }

    // Metodi modificatori
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public void addProduct(int id, int qty) {
        products.add(new CartedProduct(id, qty));
    }

    public void removeProduct(int id) {
        for (CartedProduct product : products)
            if (product.getId() == id) {
                products.remove(product);
                break;
            }
    }

    public void addQty(int id) {
        for (CartedProduct product : products)
            if (id == product.getId())
                product.setQty(product.getQty() + 1);
    }

    public void removeQty(int id) {
        for (CartedProduct product : products)
            if (id == product.getId()) {
                int qty = product.getQty();
                if (qty > 1)
                    product.setQty(qty - 1);
                else
                    products.remove(product);
            }
    }

    // Attributi
    private int id_user;
    final private ArrayList<CartedProduct> products;
}
