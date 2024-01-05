package com.blog.youngbolg.exception;

/**
 * status -> 404
 */
public class PostNotFound extends YoungBlogException{

    private static final String MESSAGE = "존재하지 않은 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
        
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
