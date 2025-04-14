package com.sensedia.sample.consents.config.exception;

import com.sensedia.sample.consents.config.exception.enums.SupportedLocales;
import com.sensedia.sample.consents.config.exception.response.ErrorMessage;

import java.util.Optional;
import java.util.ResourceBundle;

public class ErrorContext {

    private static final ThreadLocal<ErrorMessage> errorMessage = new ThreadLocal<>();

    private static ResourceBundle bundle;

    public static void setErrorMessage(ErrorMessage message, String locale) {
        errorMessage.set(message);
        bundle = ResourceBundle.getBundle("messages", SupportedLocales.fromKey(locale));
    }

    public static void add(String type, String title) {
        Optional.ofNullable(errorMessage.get()).ifPresent(message -> {
            message.setType(type);
            message.setTitle(title);
        });
    }

    public static void addError(String detail, String pointer) {
        Optional.ofNullable(errorMessage.get()).ifPresent(message -> message.addError(detail, pointer));
    }

    public static void throwError(String type, String title, String detail, String pointer) {
        Optional.ofNullable(errorMessage.get()).ifPresent(message -> {
            message.setType(bundle.getString(type));
            message.setTitle(bundle.getString(title));
            message.addError(bundle.getString(detail), pointer);
            throw new DomainException(message);
        });
    }

    public static ErrorMessage getErrorMessage() {
        return errorMessage.get();
    }

    public static void clear() {
        bundle = null;
        errorMessage.remove();
    }
}
