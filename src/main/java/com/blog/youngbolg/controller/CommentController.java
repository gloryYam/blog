package com.blog.youngbolg.controller;

import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.request.Comment.CommentCreateReq;
import com.blog.youngbolg.request.Comment.CommentDeleteReq;
import com.blog.youngbolg.service.comment.CommentFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping("/posts/{postId}/comments")
    public void write(@PathVariable Long postId,
                      @RequestBody @Valid CommentCreateReq request,
                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
        commentFacade.write(postId, request, userPrincipal);
    }

    @PostMapping("/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId,
                       @RequestBody @Valid CommentDeleteReq request,
                       @AuthenticationPrincipal UserPrincipal userPrincipal) {

        if(commentFacade.isAuthorized(commentId, userPrincipal)) {
            commentFacade.delete(commentId, request);
        }
    }
}
