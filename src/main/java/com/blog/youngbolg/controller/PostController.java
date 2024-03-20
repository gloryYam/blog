package com.blog.youngbolg.controller;

import com.blog.youngbolg.ApiResponse;
import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.request.post.PostCreateRequest;
import com.blog.youngbolg.request.post.PostEditRequest;
import com.blog.youngbolg.request.post.PostSearchRequest;
import com.blog.youngbolg.response.PostResponse;
import com.blog.youngbolg.service.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/posts")
    public ResponseEntity post(@AuthenticationPrincipal UserPrincipal userPrincipal,
                     @RequestBody @Valid PostCreateRequest request) {

        postService.write(userPrincipal.getUserId(), request.toServiceRequest());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * /posts/{postID} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public ApiResponse<PostResponse> get(@PathVariable Long postId) {
        // 서비스 정책에 맞는 응답 클래스를 분리해라
        return ApiResponse.ok(postService.get(postId));
    }

    @GetMapping("/posts")
    public Page<PostResponse> getList(@ModelAttribute PostSearchRequest postSearchRequest, Pageable pageable) {
        return postService.getList(postSearchRequest, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEditRequest request) {
        postService.edit(postId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#postId, 'POST', 'DELETE')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}

















