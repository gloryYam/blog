package com.blog.youngbolg.request.Comment;

import com.blog.youngbolg.service.comment.request.CommentCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @Size(min = 10, max = 500, message = "내용은 10~500글자로 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Size(min = 4, max = 18, message = "비밀번호는 4~18글자로 입력해주세요.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    public CommentCreateRequest(String password, String content) {
        this.content = content;
        this.password = password;
    }

    public CommentCreateServiceRequest toServiceRequest() {
        return CommentCreateServiceRequest.builder()
            .content(content)
            .password(password)
            .build();
    }
}
