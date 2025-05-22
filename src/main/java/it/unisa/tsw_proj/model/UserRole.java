package it.unisa.tsw_proj.model;

public enum UserRole {
    ADMIN,
    USER;

    public static UserRole fromDb(String dbValue) {
        return UserRole.valueOf(dbValue.toUpperCase());
    }

    public String toDb() {
        return name().toLowerCase();
    }

    public boolean isAdmin() {
        return this == UserRole.ADMIN;
    }
}
