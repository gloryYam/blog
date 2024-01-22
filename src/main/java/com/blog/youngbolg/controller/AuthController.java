package com.blog.youngbolg.controller;

import com.blog.youngbolg.config.AppConfig;
import com.blog.youngbolg.request.LoginReq;
import com.blog.youngbolg.request.SignupReq;
import com.blog.youngbolg.response.SessionResponse;
import com.blog.youngbolg.service.auth.AuthFacade;
import com.blog.youngbolg.service.auth.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthFacade authFacade;
    private final AppConfig appConfig;

    @PostMapping("/auth/LoginReq")
    public SessionResponse LoginReq(@RequestBody LoginReq LoginReq) {
        Long userId = authService.signin(LoginReq);

//        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
//                .domain("localhost")    // todo 서버 환경에 따른 분리 필요
//                .path("/")
//                .httpOnly(true)
//                .secure(false)
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict")
//                .build();
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(appConfig.getJwtKey()));
        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jws);
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody SignupReq signupReq) {
        authFacade.register(signupReq);
    }
}
