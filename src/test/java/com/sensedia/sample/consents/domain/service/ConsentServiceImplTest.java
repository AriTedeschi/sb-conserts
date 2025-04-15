package com.sensedia.sample.consents.domain.service;

import com.sensedia.sample.consents.application.adapter.request.ChangeConsentRequest;
import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;
import com.sensedia.sample.consents.config.exception.DomainException;
import com.sensedia.sample.consents.config.exception.ErrorContext;
import com.sensedia.sample.consents.config.exception.response.ErrorInfo;
import com.sensedia.sample.consents.config.exception.response.ErrorMessage;
import com.sensedia.sample.consents.domain.model.ConsentEntity;
import com.sensedia.sample.consents.domain.model.enums.StatusEnum;
import com.sensedia.sample.consents.domain.model.vo.CPFVO;
import com.sensedia.sample.consents.domain.model.vo.DateVO;
import com.sensedia.sample.consents.domain.repository.ConsentRepository;
import com.sensedia.sample.consents.domain.service.impl.ConsentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsentServiceImplTest {

    @InjectMocks
    private ConsentServiceImpl consentService;

    @Mock
    private ConsentRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        // Arrange
        CreateConsentRequest request = new CreateConsentRequest("067.793.060-70", "13/04/2025 23:15:00", null);
        ConsentEntity savedEntity = validEntity();

        when(repository.save(any(ConsentEntity.class))).thenReturn(savedEntity);

        // Act
        CreateConsentResponse response = consentService.create(request);

        // Assert
        assertNotNull(response);
        verify(repository, times(1)).save(any(ConsentEntity.class));
    }

    @Test
    void testCreate_invalid_input() {
        try(
                MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ) {
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo("CPF must have 11 digits and cannot be repeated!", "#/cpf")))));
            // Arrange
            CreateConsentRequest request = new CreateConsentRequest("111.111.111-11", "13/04/2025 23:15:00", null);
            // Act
            DomainException domainException = assertThrows(DomainException.class, () -> {
                consentService.create(request);
            });
            //Assert
            verify(repository, times(0)).save(any(ConsentEntity.class));
        }
    }

    @Test
    void testFindById_Success() {
        // Arrange
        String id = "123";
        ConsentEntity consentEntity = validEntity();
        when(repository.findById(id)).thenReturn(Optional.of(consentEntity));

        // Act
        CreateConsentResponse response = consentService.findById(id);

        // Assert
        assertNotNull(response);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        try(
                MockedStatic<ErrorContext> erroCtx = Mockito.mockStatic(ErrorContext.class)
        ) {
            erroCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any())).thenThrow(new DomainException(new ErrorMessage(null, null,
                    List.of(new ErrorInfo("Consent not found with provided id", "#/expirationDateTime")))));
            // Arrange
            String id = "123";
            when(repository.findById(id)).thenReturn(Optional.empty());
            // Act
            DomainException domainException = assertThrows(DomainException.class, () -> {
                consentService.findById(id);
            });
            //Assert
            verify(repository, times(1)).findById(id);
        }
    }

    @Test
    void change_updatesConsentSuccessfully() {
        // Arrange
        String id = "123";
        ChangeConsentRequest request = new ChangeConsentRequest("067.793.060-70", "ACTIVE", "13/04/2025 23:01:39", null);
        ConsentEntity existingEntity = validEntity();
        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repository.save(any(ConsentEntity.class))).thenReturn(existingEntity);

        // Act
        CreateConsentResponse response = consentService.change(id, request);

        // Assert
        assertNotNull(response);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(existingEntity);
    }

    @Test
    void change_throwsExceptionWhenConsentNotFound() {
        try (
                MockedStatic<ErrorContext> errorCtx = Mockito.mockStatic(ErrorContext.class)
        ) {
            errorCtx.when(() -> ErrorContext.throwError(any(), any(), any(), any()))
                    .thenThrow(new DomainException(new ErrorMessage(null, null,
                            List.of(new ErrorInfo("Consent not found with provided id", "#/id")))));
            // Arrange
            String id = "nonexistent-id";
            ChangeConsentRequest request = new ChangeConsentRequest("067.793.060-70", "ACTIVE", "13/04/2025 23:01:39", null);
            when(repository.findById(id)).thenReturn(Optional.empty());

            // Act
            DomainException exception = assertThrows(DomainException.class, () -> {
                consentService.change(id, request);
            });

            // Assert
            verify(repository, times(1)).findById(id);
            verify(repository, times(0)).save(any(ConsentEntity.class));
        }
    }

    private static ConsentEntity validEntity() {
        return ConsentEntity.builder()
                .id("389d6db5-fbdb-4d2c-a352-59fb59df9d2e")
                .cpf(new CPFVO("067.793.060-70"))
                .creationDateTime(new DateVO())
                .status(StatusEnum.ACTIVE.getId())
                .expirationDateTime(null)
                .additionalInfo(null)
                .build();
    }
}