package it.unisa.tsw_proj.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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

    public static boolean containsBadWord(String input) {
        String normalized = input.toLowerCase()
                .replace("3", "e")
                .replace("0", "o")
                .replace("1", "i")
                .replace("!", "i")
                .replace("$", "s")
                .replace("@", "a");

        for (String word : BAD_WORDS) {
            if (normalized.contains(word)) return true;
        }
        return false;
    }

    public static boolean brandValidate(String brand) {
        return brand != null && BRAND_PATTERN.matcher(brand).matches();
    }

    public static boolean modelValidate(String model) {
        return model != null && MODEL_PATTERN.matcher(model).matches();
    }

    public static boolean colorValidate(String color) {
        return color != null && COLOR_PATTERN.matcher(color).matches();
    }

    public static boolean storageStockValidate(String storageStock) {
        return storageStock != null && STORAGE_STOCK_PATTERN.matcher(storageStock).matches();
    }

    public static boolean priceValidate(String price) {
        return price != null && PRICE_PATTERN.matcher(price).matches();
    }

    // Attributi
    private static final Pattern FULL_NAME_PATTERN = Pattern.compile("^(?=.{1,50}$)\\S+(?:\\s+\\S+)+$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[!@#$%^&*(),.?\":{}|<>_])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>_]{8,16}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{1,254}$)[a-zA-Z0-9](?!.*?[.]{2})[a-zA-Z0-9._%+-]{0,63}@[a-zA-Z0-9](?!.*--)[a-zA-Z0-9.-]{0,253}\\.[a-zA-Z]{2,}$");
    private static final Pattern CELLPHONE_PATTERN = Pattern.compile("^\\+?[0-9]{9,15}$");
    private static final Pattern COUNTRY_PATTERN = Pattern.compile("^[a-z]{2}$");

    private static final Set<String> BAD_WORDS = new HashSet<>(Arrays.asList(
            "faggot", "nigger"
    ));

    private static final Pattern BRAND_PATTERN = Pattern.compile("^[\\p{L}\\p{N} .,'\\-_&+!()]{1,30}$");
    private static final Pattern MODEL_PATTERN = Pattern.compile("^[\\p{L}\\p{N} .,'\\-_&+!()]{1,50}$");
    private static final Pattern COLOR_PATTERN = Pattern.compile("^#[0-9a-fA-F]{6}$");
    private static final Pattern STORAGE_STOCK_PATTERN = Pattern.compile("^[0-9]+$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
}
