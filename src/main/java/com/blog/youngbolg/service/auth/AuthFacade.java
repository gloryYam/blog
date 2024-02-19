package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.domain.Account;
import com.blog.youngbolg.request.SignupReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;

    public Long regist(SignupReq signupReq) {
        Account account = toEntity(signupReq);
        return authService.signup(account, signupReq.getPassword2());
    }


    private Account toEntity(SignupReq signupReq) {
        return Account.builder()
                .email(signupReq.getEmail())
                .nickName(signupReq.getNickName())
                .name(signupReq.getName())
                .password(signupReq.getPassword1())
                .build();
    }
}
