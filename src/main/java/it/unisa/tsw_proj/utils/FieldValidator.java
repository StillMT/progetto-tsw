package it.unisa.tsw_proj.utils;

import java.util.regex.Pattern;

public final class FieldValidator {

    // Costruttore
    private FieldValidator() {}

    // Metodi
    public static boolean repPswValidate(String psw, String repPsw) {
        return repPsw.equals(psw);
    }

    public static boolean fullNameValidate(String fullName) {
        return fullName != null && FULL_NAME_PATTERN.matcher(fullName).matches();
    }

    public static boolean usernameValidate(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean passwordValidate(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean emailValidate(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean phoneValidate(String phone) {
        return phone != null && CELLPHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean countryValidate(String ct) {
        return ct != null && COUNTRY_PATTERN.matcher(ct).matches();
    }

    // Attributi
    private static final Pattern FULL_NAME_PATTERN = Pattern.compile("^(?=.{1,50}$)\\S+(?:\\s+\\S+)+$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[!@#$%^&*(),.?\":{}|<>_])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>_]{8,16}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{1,254}$)[a-zA-Z0-9](?!.*?[.]{2})[a-zA-Z0-9._%+-]{0,63}@[a-zA-Z0-9](?!.*--)[a-zA-Z0-9.-]{0,253}\\.[a-zA-Z]{2,}$");
    private static final Pattern CELLPHONE_PATTERN = Pattern.compile("^\\+?[0-9]{9,15}$");
    private static final Pattern COUNTRY_PATTERN = Pattern.compile("^[a-z]{2}$");
}
