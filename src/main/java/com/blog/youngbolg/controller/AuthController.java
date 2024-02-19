package com.blog.youngbolg.controller;

import com.blog.youngbolg.request.SignupReq;
import com.blog.youngbolg.service.auth.AuthFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;


    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupReq signupReq, BindingResult result) {

        if(!signupReq.getPassword1().equals(signupReq.getPassword2())) {
            result.rejectValue("password2", "passwordInCorrect",
                    "비밀번호가 일치하지 않습니다.!!");
        }

        if(result.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            for(FieldError error : result.getFieldErrors())
                sb.append(error.getDefaultMessage() + " ");

            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        authFacade.regist(signupReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
