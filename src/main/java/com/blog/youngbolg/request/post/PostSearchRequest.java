package com.blog.youngbolg.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchRequest {

    private String title;

    private String content;

    @Builder
    public PostSearchRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
