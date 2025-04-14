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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class DateVOTest {
    @DisplayName("Date validation with invalids dates")
    @ParameterizedTest(name = "Date: {0} expects {1}")
    @CsvSource(value = {
            "23/03/202a 21:11:11, Error processing date expected pattern dd/MM/yyyy HH:mm:ss",
            "null, Error processing date expected pattern dd/MM/yyyy HH:mm:ss",
            "2025/03/23 21:11:11, Error processing date expected pattern dd/MM/yyyy HH:mm:ss"
    }, nullValues = "null")
    void testNegativeScenarios(String date, String expectedError) {
        try(
                MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ){
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo(expectedError, "#/expirationDateTime")))));

            // Act
            DomainException domainException = assertThrows(DomainException.class, () -> {
               new DateVO(date);
            });

            // Assert
            assertEquals(expectedError, domainException.getErrorMessage().getErrorInfos().getFirst().getDetail());
            assertEquals("#/expirationDateTime", domainException.getErrorMessage().getErrorInfos().getFirst().getPointer());
        }
    }

    @DisplayName("Date validation with valids dates")
    @ParameterizedTest(name = "Date: {0}")
    @CsvSource(value = {
            "13/04/2025 00:00:01",
    }, nullValues = "null")
    void testPositveScenarios(String date) {
        try(
                MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ){
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo("", "#/expirationDateTime")))));

            // Act
            LocalDateTime validDate = new DateVO(date).getValue();

            assertNotNull(validDate);
        }
    }
}
