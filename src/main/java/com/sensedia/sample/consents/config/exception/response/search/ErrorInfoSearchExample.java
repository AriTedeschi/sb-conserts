package com.sensedia.sample.consents.config.exception.response.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorInfoSearchExample {
    @Schema(description = "Error detailed messase", example = "Consent not found with provided id")
    private String detail;
    @Schema(description = "Pointer to invalid field", example = "?id")
    private String pointer;
}