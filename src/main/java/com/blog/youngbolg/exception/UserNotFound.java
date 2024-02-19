package com.blog.youngbolg.exception;

public class UserNotFound extends YoungBlogException{

    private static final String MESSAGE = "존재하지 않은 사용자입니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
