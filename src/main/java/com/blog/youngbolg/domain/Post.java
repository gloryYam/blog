package com.blog.youngbolg.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="post_id")
    private Long id;

    private String title;

    @Lob // 자바에서는 String 형태 DB 에서는 Long Text 형태로 생성 되도록
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void edit(Post postEditor) {
        title = postEditor.getTitle();
        content = postEditor.getContent();
    }
}
