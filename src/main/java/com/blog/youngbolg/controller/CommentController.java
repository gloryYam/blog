package com.blog.youngbolg.controller;

import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.request.Comment.CommentCreateRequest;
import com.blog.youngbolg.request.Comment.CommentDeleteRequest;
import com.blog.youngbolg.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public void write(@PathVariable Long postId,
                      @RequestBody @Valid CommentCreateRequest request,
                      @AuthenticationPrincipal UserPrincipal userPrincipal) {

        commentService.write(postId, request.toServiceRequest(), userPrincipal.getUserId());
    }

    @PostMapping("/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId,
                       @RequestBody @Valid CommentDeleteRequest request,
                       @AuthenticationPrincipal UserPrincipal userPrincipal) {

            commentService.delete(commentId, request.toServiceRequest());
    }
}
