package com.sensedia.sample.consents.config.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEnum {
    INVALID_INPUT("consent.error.type.input"),
    INVALID_PARAMETER("consent.error.type.parameter");
    private String code;
}
