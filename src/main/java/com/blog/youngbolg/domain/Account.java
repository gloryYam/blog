package com.blog.youngbolg.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String nickName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL
    )
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Account(String name, String nickName,String email, String password, AccountRole accountRole) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.accountRole = accountRole;
        this.createdAt = LocalDateTime.now();
    }

    public void Password(String password) {
        this.password = password;
    }
}
