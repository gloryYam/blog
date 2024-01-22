package com.blog.youngbolg.request;

import lombok.Builder;
import lombok.Data;

@Data
public class SignupReq {

    private String email;
    private String name;
    private String password;

    @Builder
    public SignupReq(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
