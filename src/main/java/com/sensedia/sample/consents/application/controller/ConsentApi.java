package com.sensedia.sample.consents.application.controller;

import com.sensedia.sample.consents.application.adapter.request.ChangeConsentRequest;
import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.ConsentResponse;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;
import com.sensedia.sample.consents.domain.service.ConsentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ConsentApi implements IConsentApi {
	private static final List<String> ALLOWED_ORDER_BY = List.of("id", "cpf", "status_id", "created_at");
	private static final List<String> ALLOWED_DIRECTION = List.of("ASC", "DESC");
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

	@Override
	public ResponseEntity<CreateConsentResponse> findById(String id, String acceptLanguage, HttpServletRequest request) {
		CreateConsentResponse response = service.findById(id);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Page<ConsentResponse>> search(Integer page, Integer linesPerPage, String orderBy, String direction, Map<String, String> filters) {
		if(!ALLOWED_ORDER_BY.contains(orderBy)) orderBy=null;
		if(!ALLOWED_DIRECTION.contains(direction)) direction="DESC";
		Pageable pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return ResponseEntity.ok(service.search(pageRequest, filters));
	}

	@Override
	public ResponseEntity<CreateConsentResponse> change(String id, ChangeConsentRequest changeConsentRequest,
														String acceptLanguage, HttpServletRequest request) {
		CreateConsentResponse response = service.change(id, changeConsentRequest);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<CreateConsentResponse> revoke(String id, String acceptLanguage, HttpServletRequest request) {
		CreateConsentResponse response = service.revoke(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}

}
