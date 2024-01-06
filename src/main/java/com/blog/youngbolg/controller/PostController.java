package com.blog.youngbolg.controller;

// SSR -> jsp, thymeleaf, mustache, freemarker
// 서버렌더링 html rendering

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.request.PostCreate;
import com.blog.youngbolg.request.PostEdit;
import com.blog.youngbolg.request.PostSearch;
import com.blog.youngbolg.response.PostResponse;
import com.blog.youngbolg.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 데이터를 꺼내와서 조합하고 검증하는 거는 가능하면 하지 않는 것이 좋다
     * 메시지를 던지는 연습을 하자
     */
    @PostMapping("/posts")
    public Post post(@RequestBody @Valid PostCreate request) throws Exception {
//        if(request.getTitle().contains("바보")){
//            throw new InvalidRequest();
//        }
        request.validate();
        System.out.println("깃 명령어 테스트");

        return postService.write(request);
    }

    /**
     *  /posts/{postID} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {

        // 서비스 정책에 맞는 응답 클래스를 분리해라
        return postService.get(postId);

    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch pageable) {
        return postService.getList(pageable);
    }

    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        return postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}

















