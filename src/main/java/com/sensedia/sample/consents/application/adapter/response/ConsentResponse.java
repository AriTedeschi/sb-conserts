package com.sensedia.sample.consents.application.adapter.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Consent DTO")
public record ConsentResponse(
        @Schema(description = "Unique identifier of the consent", example = "e2b0205a-6573-4d80-b69a-537f062538aa") String id,
        @Schema(description = "CPF of the user", example = "382.***.***-65") String cpf,
        @Schema(description = "Status of the consent", example = "ACTIVE") String status,
        @Schema(description = "Creation date and time of the consent", example = "13/04/2025 23:01:39") String creationDateTime) {}
