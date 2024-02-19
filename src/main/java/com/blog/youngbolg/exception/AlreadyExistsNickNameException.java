package com.blog.youngbolg.exception;

public class AlreadyExistsNickNameException extends YoungBlogException{

    private static final String MESSAGE = "이미 존재하는 닉네임입니다.";

    public AlreadyExistsNickNameException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
