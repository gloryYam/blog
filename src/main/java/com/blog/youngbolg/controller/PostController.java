package com.blog.youngbolg.controller;

// SSR -> jsp, thymeleaf, mustache, freemarker
// 서버렌더링 html rendering

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// SPA -> javascript + <-> API (JSON)
// vue -> vue+SSR = nuxt
// react -> react+SSR - next
@RestController
public class PostController {
    // http 요청 메소드
    //GET, POST, PUT, PATCH, DELETE, CONNECT, OPTIONS, TRACE, PATCH

    @GetMapping("/posts")
    public String get() {
        return "안녕";
    }
}
