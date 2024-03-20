package com.blog.youngbolg.service.auth.request;

import com.blog.youngbolg.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupServiceRequest {

    private String email;
    private String name;
    private String password;
    private String nickName;

    @Builder
    public SignupServiceRequest(String email, String nickName, String name, String password) {
        this.email = email;
        this.nickName = nickName;
        this.name = name;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
            .email(email)
            .name(name)
            .nickName(nickName)
            .password(password)
            .build();
    }
}