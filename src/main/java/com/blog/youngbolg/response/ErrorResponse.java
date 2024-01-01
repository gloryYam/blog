package com.blog.youngbolg.response;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다.",
 *     "validation": {
 *         "title": "값을 입력해주세요"
 *     }
 * }
 */
@Data
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
    private List<Validation> validations = new ArrayList<>();

    public void addValidation(String fieldName, String errorMessage) {
        Validation validation = Validation.builder()
                .fieldName(fieldName)
                .errorMessage(errorMessage)
                .build();

        this.validations.add(validation);
    }
}








