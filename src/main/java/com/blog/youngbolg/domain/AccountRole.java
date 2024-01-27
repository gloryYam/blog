package com.blog.youngbolg.domain;

import lombok.Getter;

@Getter
public enum AccountRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;

    AccountRole(String value) {
        this.value = value;
    }
}
