package com.sensedia.sample.consents.application.controller;

import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;
import com.sensedia.sample.consents.config.exception.response.ErrorMessage;
import com.sensedia.sample.consents.config.exception.response.search.ErrorMessageSearchExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IConsentApi {

	@PostMapping("/consents")
	@Operation(
			summary = "Create consent",
			responses = {
					@ApiResponse(responseCode = "201", description = "Successful", content = @Content(schema = @Schema(implementation = CreateConsentResponse.class))),
					@ApiResponse(responseCode = "422", description = "Field validation error format", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "500", description = "Internal application error format", content = @Content(schema = @Schema(implementation = Object.class)))
			}
	)
	ResponseEntity<CreateConsentResponse> create(@RequestBody CreateConsentRequest createRequest,
												 @RequestHeader(value = "Accept-Language",
														 required = false, defaultValue = "en") String acceptLanguage,
												 HttpServletRequest request);

	@GetMapping("/consents/{id}")
	@Operation(
			summary = "Find consent by id",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = CreateConsentResponse.class))),
					@ApiResponse(responseCode = "422", description = "Field validation error format", content = @Content(schema = @Schema(implementation = ErrorMessageSearchExample.class))),
					@ApiResponse(responseCode = "500", description = "Internal application error format", content = @Content(schema = @Schema(implementation = Object.class)))
			}
	)
	ResponseEntity<CreateConsentResponse> findById(@PathVariable String id,
												 @RequestHeader(value = "Accept-Language",
														 required = false, defaultValue = "en") String acceptLanguage,
												 HttpServletRequest request);
}
