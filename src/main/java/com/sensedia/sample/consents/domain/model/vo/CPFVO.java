package com.sensedia.sample.consents.domain.model.vo;

import com.sensedia.sample.consents.config.exception.ErrorContext;
import com.sensedia.sample.consents.config.exception.enums.FieldsPointersEnum;
import com.sensedia.sample.consents.config.exception.enums.TitleEnum;
import com.sensedia.sample.consents.config.exception.enums.TypeEnum;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class CPFVO {
    private static final String ONLY_DIGITS = "[\\D]";
    private static final String NULL_CPF = "consent.cpf.null";
    private static final String INVALID = "consent.cpf.invalid";
    private static final String INVALID_DIGITS = "consent.cpf.invalid.digits";

    private String value;

    public CPFVO(String value){
        this.value = this.validate(value);
    }

    public String format(String value) {
        value = value.replaceAll(ONLY_DIGITS, "");
        return value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public String obfuscate() {
        value = value.replaceAll(ONLY_DIGITS, "");
        return value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.***.***-$4");
    }

    public String validate(String value){
        try {
            Objects.requireNonNull(value, NULL_CPF);

            value = value.replaceAll(ONLY_DIGITS, "");

            if(value.length() != 11 || value.matches("(\\d)\\1{10}")) throw new IllegalArgumentException(INVALID_DIGITS);

            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = value.charAt(i) - '0';
            }
            int sum = 0;
            for (int i = 0; i < 9; i++)
                sum += digits[i] * (10 - i);
            int remainder = sum % 11;
            int digit1 = (remainder < 2) ? 0 : (11 - remainder);

            sum = 0;
            for (int i = 0; i < 10; i++)
                sum += digits[i] * (11 - i);
            remainder = sum % 11;
            int digit2 = (remainder < 2) ? 0 : (11 - remainder);

            if(!(digit1 == digits[9] && digit2 == digits[10]))
                throw new IllegalArgumentException(INVALID);
        } catch (NullPointerException | IllegalArgumentException e){
            ErrorContext.throwError(TypeEnum.INVALID_INPUT.getCode(), TitleEnum.CREATING.getCode(), e.getMessage(), FieldsPointersEnum.CPF.getCode());
        }
        return this.format(value);
    }
}
