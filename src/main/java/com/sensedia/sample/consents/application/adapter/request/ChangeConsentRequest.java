package com.sensedia.sample.consents.application.adapter.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChangeConsentRequest(
        @Schema(description = "CPF", example = "067.793.060-70") String cpf,
        @Schema(description = "Status of the consent", example = "ACTIVE") String status,
        @Schema(description = "Expiration date and time of the consent", example = "13/04/2025 23:01:39") String expirationDateTime,
        @Schema(description = "Additional information about the consent", example = "Some additional info up to 50 characters") String additionalInfo) {}