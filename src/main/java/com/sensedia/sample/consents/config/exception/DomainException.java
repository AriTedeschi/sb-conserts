package com.sensedia.sample.consents.config.exception;

import com.sensedia.sample.consents.config.exception.response.ErrorMessage;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private ErrorMessage errorMessage;

    public DomainException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
