package com.sensedia.sample.consents.application.adapter.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Sucessful response for creating a consent")
public record CreateConsentResponse(
        @Schema(description = "Unique identifier of the consent", example = "e2b0205a-6573-4d80-b69a-537f062538aa") String id,
        @Schema(description = "CPF of the user", example = "382.***.***-65") String cpf,
        @Schema(description = "Status of the consent", example = "ACTIVE") String status,
        @Schema(description = "Creation date and time of the consent", example = "13/04/2025 23:01:39") String creationDateTime,
        @Schema(description = "Expiration date and time of the consent", example = "13/04/2025 23:01:39") String expirationDateTime,
        @Schema(description = "Additional information about the consent", example = "Some additional info up to 50 characters") String additionalInfo) {}
