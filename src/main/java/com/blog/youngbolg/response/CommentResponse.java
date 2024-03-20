package com.blog.youngbolg.response;

import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.domain.Comment;
import com.blog.youngbolg.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {

    private Long id;
    private String nickName;
    private String content;
    private String password;
    private Post post;
    private User user;

    @Builder
    public CommentResponse(Long id, String nickName, String content, String password, Post post, User user) {
        this.id = id;
        this.nickName = nickName;
        this.content = content;
        this.password = password;
        this.post = post;
        this.user = user;
    }

    public static CommentResponse of(Comment saveComment) {
        return CommentResponse.builder()
            .id(saveComment.getId())
            .nickName(saveComment.getNickName())
            .content(saveComment.getContent())
            .password(saveComment.getPassword())
            .post(saveComment.getPost())
            .user(saveComment.getUser())
            .build();
    }
}
