package com.sensedia.sample.consents.domain.model.vo;

import com.sensedia.sample.consents.config.exception.DomainException;
import com.sensedia.sample.consents.config.exception.ErrorContext;
import com.sensedia.sample.consents.config.exception.response.ErrorInfo;
import com.sensedia.sample.consents.config.exception.response.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class CPFVOTest {
    @DisplayName("CPF validation with invalids CPFs")
    @ParameterizedTest(name = "CPF: {0} expects {1}")
    @CsvSource(value = {
            "null, CPF cannot be null!",
            "123.456.789-0a, CPF must have 11 digits and cannot be repeated!",
            "111.111.111-11, CPF must have 11 digits and cannot be repeated!",
            "12345678901, false"
    }, nullValues = "null")
    void testNegativeScenarios(String cpf, String expectedError) {
        try(
                MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ){
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo(expectedError, "#/cpf")))));

            // Act
            DomainException domainException = assertThrows(DomainException.class, () -> {
               new CPFVO(cpf);
            });

            // Assert
            assertEquals(expectedError, domainException.getErrorMessage().getErrorInfos().getFirst().getDetail());
            assertEquals("#/cpf", domainException.getErrorMessage().getErrorInfos().getFirst().getPointer());
        }
    }

    @DisplayName("CPF validation with invalids CPFs")
    @ParameterizedTest(name = "CPF: {0} expects {1}")
    @CsvSource(value = {
            "382.265.070-65, 382.265.070-65",
            "38226507065, 382.265.070-65",
    }, nullValues = "null")
    void testPositveScenarios(String cpf, String expected) {
        try(
                MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ){
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo("", "#/cpf")))));

            // Act
            String result = new CPFVO(cpf).getValue();
            // Assert
            assertEquals(expected, result);
        }
    }
}
