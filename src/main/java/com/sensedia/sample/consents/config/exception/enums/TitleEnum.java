package com.sensedia.sample.consents.config.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TitleEnum {
    CREATING("consent.error.title.create"),
    SEARCHING_BY_ID("consent.error.title.searching.by.id"),
    CHANGING("consent.error.title.change"),
    DELETING("consent.error.title.delete");
    private String code;
}
