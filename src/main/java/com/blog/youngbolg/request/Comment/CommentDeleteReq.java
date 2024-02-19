package com.blog.youngbolg.request.Comment;

import lombok.Data;

@Data
public class CommentDeleteReq {

    private String password;

    public CommentDeleteReq() {

    }
    public CommentDeleteReq(String password) {
        this.password = password;
    }
}
