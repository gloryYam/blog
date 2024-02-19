package com.blog.youngbolg.exception;

public class InvalidSignInInformation extends YoungBlogException{

    private static final String MESSAGE = "아이디와 비밀번호가 올바르지 않습니다.";

    public InvalidSignInInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
