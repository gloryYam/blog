package com.blog.youngbolg.service.comment;

import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.domain.Account;
import com.blog.youngbolg.domain.Comment;
import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.request.Comment.CommentCreateReq;
import com.blog.youngbolg.request.Comment.CommentDeleteReq;
import com.blog.youngbolg.service.auth.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.blog.youngbolg.domain.AccountRole.ADMIN;

@Component
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;
    private final AuthService authService;

    @Transactional
    public void write(Long postId, CommentCreateReq request, UserPrincipal userPrincipal) {
        Post post = commentService.postFindOne(postId);
        Long userId = userPrincipal.getUserId();

        Comment comment = toEntity(request, post, userId);
        commentService.write(comment, request.getPassword());
    }

    public void delete(Long commentId, CommentDeleteReq request) {
        commentService.delete(commentId, request);
    }

    private Comment toEntity(CommentCreateReq request, Post post, Long userId) {
        Account account = authService.findOne(userId);
        return Comment.of(account.getNickName(), request.getContent(), post, account);
    }

    public boolean isAuthorized(Long commentId, UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUserId();

        Account account = authService.findOne(userId);
        Comment comment = commentService.commentFindOne(commentId);

        return account.getAccountRole() == ADMIN || Objects.equals(comment.getAccount().getId(), userId);
    }
}
