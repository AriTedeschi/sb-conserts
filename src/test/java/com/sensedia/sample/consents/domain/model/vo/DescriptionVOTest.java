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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class DescriptionVOTest {
    @DisplayName("Description validation with invalids descriptions")
    @ParameterizedTest(name = "Description: {0} expects {1}")
    @CsvSource(value = {
            "123456789012345678901234567890123456789012345678901,Description should have at most 50 characters!"
    }, nullValues = "null")
    void testNegativeScenarios(String description, String expectedError) {
        try(
                MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ){
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo(expectedError, "#/additionalInfo")))));

            // Act
            DomainException domainException = assertThrows(DomainException.class, () -> {
               new DescriptionVO(description);
            });

            // Assert
            assertEquals(expectedError, domainException.getErrorMessage().getErrorInfos().getFirst().getDetail());
            assertEquals("#/additionalInfo", domainException.getErrorMessage().getErrorInfos().getFirst().getPointer());
        }
    }

    @DisplayName("Description validation with valids descriptions")
    @ParameterizedTest(name = "Description: {0}")
    @CsvSource(value = {
            "Valid description",
    }, nullValues = "null")
    void testPositveScenarios(String description) {
        try(
                MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ){
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo("", "#/additionalInfo")))));

            // Act
            String validDescription = new DescriptionVO(description).getValue();

            assertNotNull(validDescription);
        }
    }
}
