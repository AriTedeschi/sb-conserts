package com.sensedia.sample.consents.domain.service.impl;

import com.sensedia.sample.consents.application.adapter.response.ConsentEntity2ConsentResponse;
import com.sensedia.sample.consents.application.adapter.response.ConsentEntity2CreateConsentResponse;
import com.sensedia.sample.consents.application.adapter.request.CreateConsent2ConsentEntity;
import com.sensedia.sample.consents.application.adapter.request.CreateConsentRequest;
import com.sensedia.sample.consents.application.adapter.response.ConsentResponse;
import com.sensedia.sample.consents.application.adapter.response.CreateConsentResponse;
import com.sensedia.sample.consents.config.exception.ErrorContext;
import com.sensedia.sample.consents.config.exception.enums.FieldsPointersEnum;
import com.sensedia.sample.consents.config.exception.enums.TitleEnum;
import com.sensedia.sample.consents.config.exception.enums.TypeEnum;
import com.sensedia.sample.consents.domain.model.ConsentEntity;
import com.sensedia.sample.consents.domain.model.enums.StatusEnum;
import com.sensedia.sample.consents.domain.repository.ConsentRepository;
import com.sensedia.sample.consents.domain.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class ConsentServiceImpl implements ConsentService {
    private static final String CONSENT_ID_NOT_FOUND = "consent.id.not.found";
    @Autowired
    private ConsentRepository repository;

    @Override
    public CreateConsentResponse create(CreateConsentRequest request) {
        ConsentEntity consentEntity = CreateConsent2ConsentEntity.INSTANCE.convert(request);
        ConsentEntity saved = repository.save(consentEntity);
        return ConsentEntity2CreateConsentResponse.INSTANCE.convert(saved);
    }

    @Override
    public CreateConsentResponse findById(String id) {
        return ConsentEntity2CreateConsentResponse.INSTANCE.convert(byId(id));
    }

    private ConsentEntity byId(String id) {
        return repository.findById(id).orElseThrow(() -> {
            ErrorContext.throwError(TypeEnum.INVALID_PARAMETER.getCode(),
                    TitleEnum.SEARCHING_BY_ID.getCode(),
                    CONSENT_ID_NOT_FOUND, FieldsPointersEnum.ID.getCode());
            return null;
        });
    }

    @Override
    public Page<ConsentResponse> search(Pageable pageable, Map<String, String> filters) {
        String id = filters.get("id");
        String cpf = filters.get("cpf");
        Long status = Optional.ofNullable(StatusEnum.byId(filters.get("status"))).map(StatusEnum::getId).orElse(null);
        LocalDate startsAt = filters.get("startsAt") != null ? LocalDate.parse(filters.get("startsAt")) : null;
        LocalDate endsAt = filters.get("endsAt") != null ? LocalDate.parse(filters.get("endsAt")) : null;

        return repository.search(id, cpf, status, startsAt, endsAt, pageable)
                .map(ConsentEntity2ConsentResponse.INSTANCE::convert);
    }
}
