package com.sensedia.sample.consents.config.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FieldsPointersEnum {
    CPF("#/cpf"),
    EXPERATION_DATE_TIME("#/expirationDateTime"),
    ADDITIONAL_INFO("#/additionalInfo");
    private String code;
}
