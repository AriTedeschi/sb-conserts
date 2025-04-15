package com.sensedia.sample.consents.application.adapter.response;
import com.sensedia.sample.consents.domain.model.ConsentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConsentEntity2ConsentResponse {
    ConsentEntity2ConsentResponse INSTANCE = Mappers.getMapper(ConsentEntity2ConsentResponse.class);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "cpf", expression = "java(entity.getCpf().obfuscate())")
    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    @Mapping(target = "creationDateTime", expression = "java(entity.getCreationDateTime().getValue().format(java.time.format.DateTimeFormatter.ofPattern(\"dd/MM/yyyy HH:mm:ss\")))")
    ConsentResponse convert(ConsentEntity entity);
}
