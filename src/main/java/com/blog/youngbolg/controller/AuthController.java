package com.blog.youngbolg.controller;

import com.blog.youngbolg.ApiResponse;
import com.blog.youngbolg.request.SignupRequest;
import com.blog.youngbolg.response.SignResponse;
import com.blog.youngbolg.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/auth/signup")
    public ApiResponse<SignResponse> signup(@Valid @RequestBody SignupRequest signupRequest, BindingResult result) {
        return ApiResponse.ok(authService.signup(signupRequest.toServiceRequest()));
    }
}
