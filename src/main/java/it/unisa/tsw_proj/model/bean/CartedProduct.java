package it.unisa.tsw_proj.model.bean;

public class CartedProduct {

    // Costruttore
    public CartedProduct(int id, int idProd, int idVar, int qty, boolean selected) {
        this.id = id;
        this.idProd = idProd;
        this.idVar = idVar;
        this.qty = qty;
        this.selected = selected;
    }

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public int getIdProd() {
        return idProd;
    }

    public int getIdVar() {
        return idVar;
    }

    public int getQty() {
        return qty;
    }

    public boolean getSelected() {
        return selected;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public void setIdVar(int idVar) {
        this.idVar = idVar;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // Attributi
    private int id;
    private int idProd;
    private int idVar;
    private int qty;
    private boolean selected;
}
