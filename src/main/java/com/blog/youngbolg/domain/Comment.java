package com.blog.youngbolg.domain;

import com.blog.youngbolg.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(
        indexes =
                @Index(name = "IDX_COMMENT_POST_ID", columnList = "post_id")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private User user;

    @Builder
    public Comment(String nickName, String content, String password, Post post) {
        this.nickName = nickName;
        this.content = content;
        this.password = password;
        this.post = post;
    }

    public void encodePassword(String password) {
        this.password = password;
    }

    public static Comment of(String nickName, String content, String password, Post post) {
        return new Comment(nickName, content, password, post);
    }

    public String getEmail() {
        return user.getEmail();
    }
}
