package com.blog.youngbolg.service.comment.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteServiceRequest {

    private String password;

    @Builder
    public CommentDeleteServiceRequest(String password) {
        this.password = password;
    }
}
