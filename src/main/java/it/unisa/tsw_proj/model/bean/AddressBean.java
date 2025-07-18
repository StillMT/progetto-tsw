package it.unisa.tsw_proj.model.bean;

public class AddressBean {

    // Costruttori
    public AddressBean(String street, String city, String state, String zip, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country.toUpperCase();
    }

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public int getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return street + ", " + city + " " + zip + ", " + state + ", " + country;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    // Attributi
    private int id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private int idUser;
}
