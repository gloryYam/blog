package com.blog.youngbolg.request;

import com.blog.youngbolg.domain.PostEditor;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostEditReq {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder
    public PostEditReq(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }
}
