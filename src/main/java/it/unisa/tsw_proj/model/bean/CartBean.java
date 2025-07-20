package it.unisa.tsw_proj.model.bean;

import java.util.ArrayList;
import java.util.List;

public class CartBean {

    public CartBean() {
        products = new ArrayList<>();
    }

    // Metodi di accesso
    public int getIdUser() {
        return id_user;
    }

    public List<CartedProduct> getProductList() {
        return products;
    }

    public int getCartItemsCount() {
        int count = 0;

        for (CartedProduct product : products)
            count += product.getQty();

        return count;
    }

    public int getSelectedItemsCount() {
        int count = 0;

        for (CartedProduct product : products)
            if (product.getSelected())
                count += product.getQty();

        return count;
    }

    // Metodi modificatori
    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public void addProduct(int id, int idProd, int idVar, int qty, boolean selected) {
        boolean added = false;

        for (CartedProduct p : products)
            if (p.getIdProd() == idProd && p.getIdVar() == idVar) {
                p.setQty(p.getQty() + qty);

                added = true;
                break;
            }

        if (!added)
            products.add(new CartedProduct(id, idProd, idVar, qty, selected));
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
    final private List<CartedProduct> products;
}
