package com.blog.youngbolg.response;

import com.blog.youngbolg.response.ErrorResponse;
import lombok.Builder;
import lombok.Data;

@Data
public class Validation {

    private String fieldName;
    private String errorMessage;

    @Builder
    public Validation(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }
}

