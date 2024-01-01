package com.blog.youngbolg.controller;

// SSR -> jsp, thymeleaf, mustache, freemarker
// 서버렌더링 html rendering

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.request.PostCreate;
import com.blog.youngbolg.response.PostResponse;
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
    public Post post(@RequestBody @Valid PostCreate request) throws Exception {
        return postService.write(request);
    }

    /**
     *  /posts -> 글 저체 조회 ( 검색 + 페이징 )
     *  /posts/{postID} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {

        // 서비스 정책에 맞는 응답 클래스를 분리해라
        PostResponse response = postService.get(id);

        return response;
    }
}

















