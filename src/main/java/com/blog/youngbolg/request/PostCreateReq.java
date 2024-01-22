package com.blog.youngbolg.request;

import com.blog.youngbolg.exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
public class PostCreateReq {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private final String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private final String content;

    @Builder
    public PostCreateReq(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 던진다.
    public void validate() {
        if(title.contains("바보")) {
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }
}
