package com.sensedia.sample.consents.domain.model.vo;

import com.sensedia.sample.consents.config.exception.ErrorContext;
import com.sensedia.sample.consents.config.exception.enums.FieldsPointersEnum;
import com.sensedia.sample.consents.config.exception.enums.TitleEnum;
import com.sensedia.sample.consents.config.exception.enums.TypeEnum;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Embeddable
@Getter
public class DateVO {

    private static final String INVALID_DATE_PATTERN = "consent.expiration.date.invalid";

    private LocalDateTime value;

    public DateVO(){this.value = LocalDateTime.now();}

    public DateVO(String value){
        this.value = this.validate(value, FieldsPointersEnum.EXPERATION_DATE_TIME.getCode());
    }

    public LocalDateTime validate(String value, String fieldName){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return LocalDateTime.parse(value, formatter);
        } catch (DateTimeParseException | NullPointerException e){
            ErrorContext.throwError(TypeEnum.INVALID_INPUT.getCode(), TitleEnum.CREATING.getCode(),
                    INVALID_DATE_PATTERN, fieldName);
        }
        return null;
    }
}
