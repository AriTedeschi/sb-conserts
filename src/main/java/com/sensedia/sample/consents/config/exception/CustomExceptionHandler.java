package com.sensedia.sample.consents.config.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Hidden // Hides this class from Swagger documentation
public class CustomExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleValidationException(
            DomainException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getErrorMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
