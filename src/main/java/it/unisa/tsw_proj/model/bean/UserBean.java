package it.unisa.tsw_proj.model.bean;

import it.unisa.tsw_proj.model.UserRole;

public class UserBean {

    // Costruttori
    public UserBean() {}

    public UserBean(String fullName, String username, String password, String email, String phone, String nationality) {    // Register related
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.nationality = nationality;
        this.role = UserRole.USER;
    }

    public UserBean(int id, String fullName, String username, String password, String email, String phone, String nationality, UserRole role) {     // Login related
        this(fullName, username, password, email, phone, nationality);
        setId(id);
        setRole(role);
    }

    // Metodi di accesso
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getNationality() {
        return nationality;
    }

    public UserRole getRole() {
        return role;
    }

    // Metodi modificatori
    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // Attributi
    private int id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String nationality;
    private UserRole role;
}
