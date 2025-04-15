package com.sensedia.sample.consents.domain.service;

import com.sensedia.sample.consents.application.adapter.request.ChangeConsentRequest;
import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.ConsentResponse;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ConsentService {
    CreateConsentResponse create(CreateConsentRequest request);
    CreateConsentResponse findById(String id);
    Page<ConsentResponse> search(Pageable pageable, Map<String, String> filters);
    CreateConsentResponse change(String id, ChangeConsentRequest changeConsentRequest);
}
