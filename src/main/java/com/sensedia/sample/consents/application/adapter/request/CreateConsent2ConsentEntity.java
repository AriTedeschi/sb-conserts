package com.sensedia.sample.consents.application.adapter.request;

import com.sensedia.sample.consents.domain.model.ConsentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateConsent2ConsentEntity {
    CreateConsent2ConsentEntity INSTANCE = Mappers.getMapper(CreateConsent2ConsentEntity.class);

    @Mapping(target = "cpf", expression = "java(new com.sensedia.sample.consents.domain.model.vo.CPFVO(request.cpf()))")
    @Mapping(target = "creationDateTime", expression = "java(new com.sensedia.sample.consents.domain.model.vo.DateVO())")
    @Mapping(target = "expirationDateTime", expression = "java(request.expirationDateTime() != null ? (new com.sensedia.sample.consents.domain.model.vo.DateVO(request.expirationDateTime())) : null)")
    @Mapping(target = "status", expression = "java(request.expirationDateTime() == null ? com.sensedia.sample.consents.domain.model.enums.StatusEnum.ACTIVE.getId() : com.sensedia.sample.consents.domain.model.enums.StatusEnum.EXPIRED.getId())")
    @Mapping(target = "additionalInfo", expression = "java(request.additionalInfo())")
    ConsentEntity convert(CreateConsentRequest request);
}
