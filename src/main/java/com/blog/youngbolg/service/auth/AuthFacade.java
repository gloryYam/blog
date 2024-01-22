package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.request.SignupReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;

    public Long register(SignupReq signupReq) {
        User user = toEntity(signupReq);
        return authService.signup(user);
    }


    public User toEntity(SignupReq signupReq) {
        return User.builder()
                .email(signupReq.getEmail())
                .name(signupReq.getName())
                .password(signupReq.getPassword())
                .build();
    }
}