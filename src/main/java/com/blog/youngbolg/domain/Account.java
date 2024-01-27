package com.blog.youngbolg.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    private LocalDateTime createdAt;

    @Builder
    public Account(Long id, String name, String email, String password, AccountRole accountRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountRole = accountRole;
        this.createdAt = LocalDateTime.now();
    }

    public void Password(String password) {
        this.password = password;
    }


}
