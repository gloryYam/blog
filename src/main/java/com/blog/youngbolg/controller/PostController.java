package com.blog.youngbolg.controller;

// SSR -> jsp, thymeleaf, mustache, freemarker
// 서버렌더링 html rendering

import com.blog.youngbolg.request.PostCreate;
import com.blog.youngbolg.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// SPA -> javascript + <-> API (JSON)
// vue -> vue+SSR = nuxt
// react -> react+SSR - next

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    /**
     * 1. @RequestParam String tile, @RequestParam String content
     * 2. @RequestParam Map<String, String> prams
     * 3. Dto
     */
    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) throws Exception {

        postService.write(request);
        return Map.of();
    }
}

















