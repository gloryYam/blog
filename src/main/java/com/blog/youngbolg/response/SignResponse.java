package com.blog.youngbolg.response;

import com.blog.youngbolg.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignResponse {

    private Long id;
    private String name;
    private String nickName;
    private String email;
    private String password;

    @Builder
    public SignResponse(Long id, String name, String nickName, String email, String password) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

    public static SignResponse of(User saveUser) {
        return SignResponse.builder()
            .id(saveUser.getId())
            .name(saveUser.getName())
            .nickName(saveUser.getNickName())
            .email(saveUser.getEmail())
            .password(saveUser.getPassword())
            .build();
    }
}
