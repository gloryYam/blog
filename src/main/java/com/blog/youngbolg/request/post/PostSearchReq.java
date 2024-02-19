package com.blog.youngbolg.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchReq {

    private String title;

    private String content;

    @Builder
    public PostSearchReq(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
