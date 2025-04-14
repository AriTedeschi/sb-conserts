package com.sensedia.sample.consents.domain.model.vo;

import com.sensedia.sample.consents.config.exception.ErrorContext;
import com.sensedia.sample.consents.config.exception.enums.FieldsPointersEnum;
import com.sensedia.sample.consents.config.exception.enums.TitleEnum;
import com.sensedia.sample.consents.config.exception.enums.TypeEnum;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.isNull;

@Embeddable
@Getter
@NoArgsConstructor
public class DescriptionVO {
    private static final String MIN = "consent.description.min";
    private static final String MAX = "consent.description.max";

    private String value;

    public DescriptionVO(String value) {
        this.value = this.validate(value);
    }

    public String validate(String value) {
        if(isNull(value))
            return null;

        if(value.isEmpty())
            ErrorContext.throwError(TypeEnum.INVALID_INPUT.getCode(), TitleEnum.CREATING.getCode(), MIN, FieldsPointersEnum.ADDITIONAL_INFO.getCode());
        if(value.length() > 50)
            ErrorContext.throwError(TypeEnum.INVALID_INPUT.getCode(), TitleEnum.CREATING.getCode(), MAX, FieldsPointersEnum.ADDITIONAL_INFO.getCode());
        return value;
    }
}
