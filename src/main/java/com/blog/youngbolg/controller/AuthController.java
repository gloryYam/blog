package com.blog.youngbolg.controller;

import com.blog.youngbolg.request.Login;
import com.blog.youngbolg.response.SessionResponse;
import com.blog.youngbolg.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        String accessToken = authService.sign(login);

        return new SessionResponse(accessToken);
    }
}
