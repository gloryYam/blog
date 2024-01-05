package com.blog.youngbolg.exception;

import com.blog.youngbolg.response.Validation;
import lombok.Getter;

@Getter
public abstract class YoungBlogException extends RuntimeException{

    private Validation validation;

    public YoungBlogException(String message) {
        super(message);
    }

    public YoungBlogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        Validation build = Validation.builder()
                .fieldName(fieldName)
                .errorMessage(message)
                .build();
        this.validation = build;
    }
}
