package com.blog.youngbolg.controller;

// SSR -> jsp, thymeleaf, mustache, freemarker
// 서버렌더링 html rendering

import com.blog.youngbolg.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// SPA -> javascript + <-> API (JSON)
// vue -> vue+SSR = nuxt
// react -> react+SSR - next

@Slf4j
@RestController
public class PostController {
    // http 요청 메소드
    //GET, POST, PUT, PATCH, DELETE, CONNECT, OPTIONS, TRACE, PATCH
    // 글 등록
    // POST Method

    /**
     * 1. @RequestParam String tile, @RequestParam String content
     * 2. @RequestParam Map<String, String> prams
     * 3. Dto
     */
    @PostMapping("/posts")
    public String post(@RequestBody PostCreate params) {
        log.info("params={}", params.toString());

        return "안녕";
    }
}

















