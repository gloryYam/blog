package com.blog.youngbolg.request.Comment;

import com.blog.youngbolg.service.comment.request.CommentDeleteServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteRequest {

    private String password;

    public CommentDeleteRequest(String password) {
        this.password = password;
    }

    public CommentDeleteServiceRequest toServiceRequest() {
        return CommentDeleteServiceRequest.builder()
            .password(password)
            .build();
    }
}
