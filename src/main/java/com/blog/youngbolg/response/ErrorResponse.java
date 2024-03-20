package com.blog.youngbolg.response;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    private final String code;
    private final String message;
    private List<Validation> validations;

    @Builder
    public ErrorResponse(String code, String message, Validation validation) {
        this.code = code;
        this.message = message;
        this.validations = new ArrayList<>();
        if(validation != null) {
            this.validations.add(validation);
        }
    }


    public void addValidation(String fieldName, String errorMessage) {
        Validation validation = Validation.builder()
                .fieldName(fieldName)
                .errorMessage(errorMessage)
                .build();

        this.validations.add(validation);
    }
}








