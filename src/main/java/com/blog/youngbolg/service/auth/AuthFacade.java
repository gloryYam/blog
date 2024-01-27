package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.domain.Account;
import com.blog.youngbolg.request.SignupReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;

    public Long register(SignupReq signupReq) {
        Account account = toEntity(signupReq);
        return authService.signup(account);
    }


    public Account toEntity(SignupReq signupReq) {
        return Account.builder()
                .email(signupReq.getEmail())
                .name(signupReq.getName())
                .password(signupReq.getPassword())
                .build();
    }
}
