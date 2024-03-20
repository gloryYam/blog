package com.blog.youngbolg.request.post;

import com.blog.youngbolg.service.post.request.PostCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostCreateRequest {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private final String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private final String content;

    @Builder
    public PostCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostCreateServiceRequest toServiceRequest() {
        return PostCreateServiceRequest.builder()
            .title(title)
            .content(content)
            .build();
    }
}
