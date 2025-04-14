package com.sensedia.sample.consents.application.adapter;

import com.sensedia.sample.consents.application.adapter.request.CreateConsent2ConsentEntity;
import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.config.exception.DomainException;
import com.sensedia.sample.consents.config.exception.ErrorContext;
import com.sensedia.sample.consents.config.exception.response.ErrorInfo;
import com.sensedia.sample.consents.config.exception.response.ErrorMessage;
import com.sensedia.sample.consents.domain.model.ConsentEntity;
import com.sensedia.sample.consents.domain.model.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CreateConsent2ConsentEntityTest {

    @Test
    void testConvert() {
        // Arrange
        String cpf = "382.265.070-65";
        String expirationDateTime = "31/12/2023 23:59:59";
        String additionalInfo = "Test Info";

        CreateConsentRequest request = new CreateConsentRequest(cpf, expirationDateTime, additionalInfo);

        // Act
        ConsentEntity consentEntity = CreateConsent2ConsentEntity.INSTANCE.convert(request);

        // Assert
        assertNotNull(consentEntity.getCpf());
        assertEquals(cpf, consentEntity.getCpf().getValue());
        assertNotNull(consentEntity.getCreationDateTime());
        assertEquals(LocalDateTime.parse(expirationDateTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), consentEntity.getExpirationDateTime().getValue());
        assertEquals(StatusEnum.EXPIRED, consentEntity.getStatus());
        assertEquals(additionalInfo, consentEntity.getAdditionalInfo());
    }

    @Test
    void testConvertWithInvalidCPF() {
        try(
            MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ){
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo("Invalid CPF!", "#/cpf")))));
            // Arrange
            String cpf = "12345678901";

            // Act
            DomainException domainException = assertThrows(DomainException.class, () -> {
                CreateConsentRequest request = new CreateConsentRequest(cpf, null, null);
                ConsentEntity consentEntity = CreateConsent2ConsentEntity.INSTANCE.convert(request);
            });

            // Assert
            assertEquals("Invalid CPF!", domainException.getErrorMessage().getErrorInfos().getFirst().getDetail());
            assertEquals("#/cpf", domainException.getErrorMessage().getErrorInfos().getFirst().getPointer());
        }
    }

    @Test
    void testConvertWithNullExpirationDateTime() {
        // Arrange
        String cpf = "382.265.070-65";
        String additionalInfo = "Test Info";

        CreateConsentRequest request = new CreateConsentRequest(cpf, null, additionalInfo);

        // Act
        ConsentEntity consentEntity = CreateConsent2ConsentEntity.INSTANCE.convert(request);

        // Assert
        assertNotNull(consentEntity.getCreationDateTime());
        assertNull(consentEntity.getExpirationDateTime());
        assertEquals(StatusEnum.ACTIVE, consentEntity.getStatus());
        assertEquals(additionalInfo, consentEntity.getAdditionalInfo());
    }
}
