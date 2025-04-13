package com.sensedia.sample.consents.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusEnum {
    ACTIVE(1L),
    REVOKED(2L),
    EXPIRED(3L);

    private Long id;

    public StatusEnum byId(Long id)  {
        return Arrays.stream(values())
                .filter(s -> s.id.equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}