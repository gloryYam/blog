package com.blog.youngbolg.request;

import com.blog.youngbolg.service.auth.request.SignupServiceRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 1, max = 10, message = "닉네임은 최대 10글자까지 입니다.")
    private String nickName;

    @Builder
    public SignupRequest(String email, String nickName, String name, String password) {
        this.email = email;
        this.nickName = nickName;
        this.name = name;
        this.password = password;
    }

    public SignupServiceRequest toServiceRequest() {
        return SignupServiceRequest.builder()
            .email(email)
            .nickName(nickName)
            .name(name)
            .password(password)
            .build();
    }
}
