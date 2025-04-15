package com.sensedia.sample.consents.application.adapter.request;

import com.sensedia.sample.consents.domain.model.ConsentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChangeConsent2ConsentEntity {
    ChangeConsent2ConsentEntity INSTANCE = Mappers.getMapper(ChangeConsent2ConsentEntity.class);

    @Mapping(target = "cpf", expression = "java(new com.sensedia.sample.consents.domain.model.vo.CPFVO(request.cpf()))")
    @Mapping(target = "expirationDateTime", expression = "java(request.expirationDateTime() != null ? (new com.sensedia.sample.consents.domain.model.vo.DateVO(request.expirationDateTime())) : null)")
    @Mapping(target = "status", expression = "java(request.expirationDateTime() == null ? com.sensedia.sample.consents.domain.model.enums.StatusEnum.ACTIVE : com.sensedia.sample.consents.domain.model.enums.StatusEnum.EXPIRED)")
    @Mapping(target = "additionalInfo", expression = "java(new com.sensedia.sample.consents.domain.model.vo.DescriptionVO(request.additionalInfo()))")
    void convert(ChangeConsentRequest request, @MappingTarget ConsentEntity consentEntity);
}