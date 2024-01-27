package com.blog.youngbolg.controller;

import com.blog.youngbolg.request.SignupReq;
import com.blog.youngbolg.service.auth.AuthFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;


    @PostMapping("/auth/signup")
    public void signup(@RequestBody SignupReq signupReq) {
        authFacade.register(signupReq);
    }
}
