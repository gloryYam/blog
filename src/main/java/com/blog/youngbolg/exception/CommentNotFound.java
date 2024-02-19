package com.blog.youngbolg.exception;

public class CommentNotFound extends YoungBlogException{

    private static final String MESSAGE = "존재하지 않은 댓글입니다.";

    public CommentNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
