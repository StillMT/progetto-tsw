package it.unisa.tsw_proj.model.bean;

public class CartedProduct {

    // Costruttore
    public CartedProduct(int id, int qty) {
        this.id = id;
        this.qty = qty;
    }

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    // Attributi
    private int id;
    private int qty;
}
