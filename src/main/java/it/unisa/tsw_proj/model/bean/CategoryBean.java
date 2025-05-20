package it.unisa.tsw_proj.model.bean;

public class CategoryBean {

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Attributi
    private int id;
    private String name;
}
