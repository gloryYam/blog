package com.blog.youngbolg.service.post.request;

import com.blog.youngbolg.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostCreateServiceRequest {

    private final String title;
    private final String content;

    @Builder
    public PostCreateServiceRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public Post toEntity() {
        return Post.builder()
            .title(title)
            .content(content)
            .build();
    }
}
