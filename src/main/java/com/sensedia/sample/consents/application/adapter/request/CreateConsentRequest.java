package com.sensedia.sample.consents.application.adapter.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateConsentRequest(

        @Schema(description = "CPF", example = "067.793.060-70") String cpf,
        @Schema(description = "Expiration Datetime as DD/MM/YYYY HH:MM:SS", example = "13/04/2025 23:15:00") String expirationDateTime,
        @Schema(description = "Optional info", example = "I do consent ...") String additionalInfo){}