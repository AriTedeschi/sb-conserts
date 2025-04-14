package com.sensedia.sample.consents.config.exception.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorInfo {
    @Schema(description = "Error detailed messase", example = "CPF must have 11 digits and cannot be repeated!")
    private String detail;
    @Schema(description = "Pointer to invalid field", example = "#/cpf")
    private String pointer;
}