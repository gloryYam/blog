package com.blog.youngbolg.request.Comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateReq {

    @Size(min = 10, max = 500, message = "내용은 10~500글자로 입력해주세요.")
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Size(min = 4, max = 18, message = "비밀번호는 4~18글자로 입력해주세요.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    public CommentCreateReq(String password, String content) {
        this.content = content;
        this.password = password;
    }
}
