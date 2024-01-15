package com.blog.youngbolg.exception;

import lombok.Getter;

/**
 * status -> 400
 * fieldName, message를 따로 받는 것은 좋지 못하다.
 */
@Getter
public class InvalidRequest extends YoungBlogException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);

        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
