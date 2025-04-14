package com.sensedia.sample.consents.application.controller;

import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;
import com.sensedia.sample.consents.domain.service.ConsentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@Slf4j
public class ConsentApi implements IConsentApi {
	@Autowired
	private ConsentService service;

	@Override
	public ResponseEntity<CreateConsentResponse> create(@RequestBody CreateConsentRequest createRequest,
														@RequestHeader(value = "Accept-Language",
																	required = false, defaultValue = "en") String acceptLanguage,
														HttpServletRequest request) {
		CreateConsentResponse response = service.create(createRequest);
		URI location = ServletUriComponentsBuilder.fromRequestUri(request)
				.path("/{id}")
				.buildAndExpand(response.id())
				.toUri();
		return ResponseEntity.created(location).body(response);
	}
}
