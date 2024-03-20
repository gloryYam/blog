package com.blog.youngbolg.service.comment.request;

import com.blog.youngbolg.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateServiceRequest {

    private String content;
    private String password;

    @Builder
    public CommentCreateServiceRequest(String password, String content) {
        this.content = content;
        this.password = password;
    }

    public Comment toEntity() {
        return Comment.builder()
            .content(content)
            .build();
    }
}
