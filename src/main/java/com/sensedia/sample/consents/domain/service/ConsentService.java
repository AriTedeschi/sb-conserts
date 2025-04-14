package com.sensedia.sample.consents.domain.service;

import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;

public interface ConsentService {
    CreateConsentResponse create(CreateConsentRequest request);
}
