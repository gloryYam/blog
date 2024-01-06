package com.blog.youngbolg.response;

import lombok.Builder;
import lombok.Data;

@Data
public class Validation {

    private final String fieldName;
    private final String errorMessage;

    @Builder
    public Validation(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }
}

