package com.sensedia.sample.consents.application.controller;

import com.sensedia.sample.consents.application.adapter.request.ChangeConsentRequest;
import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.ConsentResponse;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;
import com.sensedia.sample.consents.config.exception.response.ErrorMessage;
import com.sensedia.sample.consents.config.exception.response.search.ErrorMessageSearchExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
												 @Parameter(description = "Optional language header, accepts en=English and pt=Portuguese")
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
												   @Parameter(description = "Optional language header, accepts en=English and pt=Portuguese")
												   @RequestHeader(value = "Accept-Language", required = false, defaultValue = "en") String acceptLanguage,
												   HttpServletRequest request);
	@GetMapping("/consents")
	@Operation(
			summary = "Search consents",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = ConsentResponse.class)))
			}
	)
	ResponseEntity<Page<ConsentResponse>> search(
			@RequestParam(value = "page", defaultValue = "0") 		    Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "10")  Integer linesPerPage,

			@Parameter(description = "Optional order by: id, cpf, status_id, created_at")
			@RequestParam(value = "orderBy", defaultValue = "id")       String 	orderBy,

			@Parameter(description = "Order by direction: ASC or DESC")
			@RequestParam(value = "direction", defaultValue = "DESC") 	String 	direction,

			@Parameter(description = "Optional filters like id, cpf, status, startsAt and endsAt")
			@RequestParam(defaultValue = "{ \"status\": \"EXPIRED\"}") Map<String, String> filters);

	@PutMapping("/consents/{id}")
	@Operation(
			summary = "Change consent by id",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = ChangeConsentRequest.class))),
					@ApiResponse(responseCode = "422", description = "Field validation error format", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
			}
	)
	ResponseEntity<CreateConsentResponse> change(@PathVariable String id,
												 @RequestBody ChangeConsentRequest createRequest,
												 @Parameter(description = "Optional language header, accepts en=English and pt=Portuguese")
												 @RequestHeader(value = "Accept-Language", required = false, defaultValue = "en")
												 String acceptLanguage,
												 HttpServletRequest request);

	@DeleteMapping("/consents/{id}")
	@Operation(
			summary = "Revoke consent by id",
			responses = {
					@ApiResponse(responseCode = "204", description = "Successful"),
					@ApiResponse(responseCode = "422", description = "Field validation error format", content = @Content(schema = @Schema(implementation = ErrorMessageSearchExample.class))),
			}
	)
	ResponseEntity<CreateConsentResponse> revoke(@PathVariable String id,
												 @Parameter(description = "Optional language header, accepts en=English and pt=Portuguese")
												 @RequestHeader(value = "Accept-Language", required = false, defaultValue = "en")
												 String acceptLanguage,
												 HttpServletRequest request);
}
