package com.www.scheduleer.web.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Auth {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String key;
}
