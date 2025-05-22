package it.unisa.tsw_proj.utils;

import java.util.Arrays;
import java.util.List;

public final class LocaleSupportedChecker {
    private LocaleSupportedChecker() {}

    public static boolean checkLanguage(String lang) {
        return SUPPORTED_LANGUAGES.contains(lang);
    }

    public static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("it", "en");
}