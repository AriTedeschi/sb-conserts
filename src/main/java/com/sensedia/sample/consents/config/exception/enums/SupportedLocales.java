package com.sensedia.sample.consents.config.exception.enums;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public enum SupportedLocales {
    PORTUGUESE("pt", new Locale.Builder().setLanguage("pt").build()),
    ENGLISH("en", new Locale.Builder().setLanguage("en").build());

    private final String key;
    private final Locale locale;
    private static final String DEFAULT_ERROR = "Something went wrong!";

    SupportedLocales(String key, Locale locale) {
        this.key = key;
        this.locale = locale;
    }

    public String getKey() {
        return key;
    }

    public Locale getLocale() {
        return locale;
    }

    public static Locale fromKey(String key) {
        return Arrays.stream(values())
                .filter(locale -> locale.getKey().equalsIgnoreCase(key))
                .map(SupportedLocales::getLocale)
                .findFirst()
                .orElse(ENGLISH.getLocale());
    }

    public static String getMessage(String locale, String messageId) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", fromKey(locale));
        return Optional.ofNullable(messageId).map(bundle::getString).orElse(DEFAULT_ERROR);
    }
}
