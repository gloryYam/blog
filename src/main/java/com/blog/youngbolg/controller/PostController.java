package com.blog.youngbolg.controller;

import com.blog.youngbolg.request.post.PostCreateReq;
import com.blog.youngbolg.request.post.PostEditReq;
import com.blog.youngbolg.request.post.PostSearchReq;
import com.blog.youngbolg.response.PostResponse;
import com.blog.youngbolg.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreateReq request) throws Exception {

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearchReq pageable) {
        return postService.getList(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/posts/{postId}")
    public PostResponse edit(@PathVariable Long postId, @RequestBody @Valid PostEditReq request) {
        return postService.edit(postId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#postId, 'POST', 'DELETE')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}

















