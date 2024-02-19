package com.blog.youngbolg.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class SignupReq {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String password2;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 10, message = "닉네임은 최대 10글자까지 입니다.")
    private String nickName;

    @Builder
    public SignupReq(String email, String nickName, String name, String password1, String password2) {
        this.email = email;
        this.nickName = nickName;
        this.name = name;
        this.password1 = password1;
        this.password2 = password2;
    }
}
