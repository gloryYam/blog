package com.blog.youngbolg.controller;

import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.request.post.PostCreateReq;
import com.blog.youngbolg.request.post.PostEditReq;
import com.blog.youngbolg.request.post.PostSearchReq;
import com.blog.youngbolg.response.PostResponse;
import com.blog.youngbolg.service.post.PostFacade;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostFacade postFacade;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/posts")
    public ResponseEntity post(@AuthenticationPrincipal UserPrincipal userPrincipal,
                     @RequestBody @Valid PostCreateReq request,
                     BindingResult result) {

        if(result.hasErrors()) {
            log.info("postWriteError ={}", result);

            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        postService.write(userPrincipal.getUserId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public Page<PostResponse> getList(@ModelAttribute PostSearchReq postSearchReq, Pageable pageable) {
        return postFacade.findPosts(postSearchReq, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEditReq request) {
        postService.edit(postId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') && hasPermission(#postId, 'POST', 'DELETE')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}

















