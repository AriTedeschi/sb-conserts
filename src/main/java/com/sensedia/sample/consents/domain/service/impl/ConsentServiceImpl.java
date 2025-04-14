package com.sensedia.sample.consents.domain.service.impl;

import com.sensedia.sample.consents.application.adapter.response.ConsentEntity2CreateConsentResponse;
import com.sensedia.sample.consents.application.adapter.request.CreateConsent2ConsentEntity;
import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;
import com.sensedia.sample.consents.domain.model.ConsentEntity;
import com.sensedia.sample.consents.domain.repository.ConsentRepository;
import com.sensedia.sample.consents.domain.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsentServiceImpl implements ConsentService {
    @Autowired
    private ConsentRepository repository;

    @Override
    public CreateConsentResponse create(CreateConsentRequest request) {
        ConsentEntity consentEntity = CreateConsent2ConsentEntity.INSTANCE.convert(request);
        ConsentEntity saved = repository.save(consentEntity);
        return ConsentEntity2CreateConsentResponse.INSTANCE.convert(saved);
    }
}
