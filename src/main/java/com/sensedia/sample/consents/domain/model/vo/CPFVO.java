package com.sensedia.sample.consents.domain.model.vo;

import java.util.Objects;

public class CPFVO {
    private static final String ONLY_DIGITS = "[\\D]";

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
        Objects.requireNonNull(value, "CPF cannot be null!");

        value = value.replaceAll(ONLY_DIGITS, "");

        if(value.length() != 11 || value.matches("(\\d)\\1{10}")) throw new IllegalArgumentException("CPF must have 11 digits and cannot be repeated!");

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
            throw new IllegalArgumentException("Invalid CPF!");
        return this.format(value);
    }
}
