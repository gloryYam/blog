package com.blog.youngbolg.domain;

import com.blog.youngbolg.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="post_id")
    private Long id;

    private String title;

    @Lob // 자바에서는 String 형태 DB 에서는 Long Text 형태로 생성 되도록
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post of(Post post, User user) {
        return new Post(post.title, post.content, user);
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);
    }

    public Long getUserId() {
        return this.user.getId();
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
