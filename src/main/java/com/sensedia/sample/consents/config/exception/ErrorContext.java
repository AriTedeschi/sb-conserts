package com.sensedia.sample.consents.config.exception;

import com.sensedia.sample.consents.config.exception.enums.SupportedLocales;
import com.sensedia.sample.consents.config.exception.enums.TitleEnum;
import com.sensedia.sample.consents.config.exception.response.ErrorMessage;

import java.util.Optional;
import java.util.ResourceBundle;

public class ErrorContext {
    private ErrorContext() {
        // Constructor is private to prevent instantiation
    }

    private static final ThreadLocal<ErrorMessage> errorMessage = new ThreadLocal<>();

    private static ResourceBundle bundle;
    private static TitleEnum operation;

    public static void setErrorMessage(ErrorMessage message, String locale, TitleEnum op) {
        operation = op;
        errorMessage.set(message);
        bundle = ResourceBundle.getBundle("messages", SupportedLocales.fromKey(locale));
    }

    public static void throwError(String type, String title, String detail, String pointer) {
        Optional.ofNullable(errorMessage.get()).ifPresent(message -> {
            message.setType(bundle.getString(type));
            message.setTitle(bundle.getString(Optional.ofNullable(operation).map(TitleEnum::getCode).orElse(title)));
            message.addError(bundle.getString(detail), pointer);
            throw new DomainException(message);
        });
    }

    public static void clear() {
        bundle = null;
        errorMessage.remove();
    }
}
