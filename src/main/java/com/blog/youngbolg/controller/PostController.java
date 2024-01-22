package com.blog.youngbolg.controller;

import com.blog.youngbolg.config.data.UserSession;
import com.blog.youngbolg.request.PostCreateReq;
import com.blog.youngbolg.request.PostEditReq;
import com.blog.youngbolg.request.PostSearchReq;
import com.blog.youngbolg.response.PostResponse;
import com.blog.youngbolg.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/glory")
    public Long glory(UserSession userSession) {
        log.info(">>>{}", userSession.id);

        return userSession.id;
    }

    @GetMapping("/yam")
    public String yam() {
        return "인증이 필요 없는 페이지";
    }

    /**
     * 1. @RequestParam String tile, @RequestParam String content
     * 2. @RequestParam Map<String, String> prams
     * 3. Dto
     * 데이터를 꺼내와서 조합하고 검증하는 거는 가능하면 하지 않는 것이 좋다
     * 메시지를 던지는 연습을 하자
     */
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreateReq request) throws Exception {
        request.validate();

        postService.write(request);
    }

    /**
     * /posts/{postID} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {

        // 서비스 정책에 맞는 응답 클래스를 분리해라
        return postService.get(postId);

    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearchReq pageable) {
        return postService.getList(pageable);
    }

    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEditReq request) {
        return postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}

















