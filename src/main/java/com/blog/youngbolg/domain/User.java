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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Session> sessions = new ArrayList<>();

    @Builder
    public User(Long id, String name, String email, String password, LocalDateTime createdAt, List<Session> sessions) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public void Password(String password) {
        this.password = password;
    }

    public Session addSession() {
        Session session = Session.builder()
                .user(this)
                .build();

        sessions.add(session);

        return session;
    }

}
