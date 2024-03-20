package com.blog.youngbolg.response;

import com.blog.youngbolg.domain.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 맞는 클래스
 */
@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    @QueryProjection
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static PostResponse of(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .build();
    }
}
