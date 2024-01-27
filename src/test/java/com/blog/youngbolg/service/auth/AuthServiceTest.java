package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.domain.Account;
import com.blog.youngbolg.exception.AlreadyExistsEmailException;
import com.blog.youngbolg.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void successSignup() {
        // given
        Account signupReq = Account.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        // when
        authService.signup(signupReq);

        // then
        assertEquals(1, userRepository.count());

        Account account = userRepository.findAll().iterator().next();
        assertEquals("dudrhkd4179@naver.com", account.getEmail());
        assertNotNull(account.getPassword());
        assertNotEquals("1234", account.getPassword());
        assertEquals("김영광", account.getName());

    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void alreadyExistsEmail() {
        // given
        Account account1 = Account.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        userRepository.save(account1);

        Account account2 = Account.builder()
                .name("바나나")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        // when
        Assertions.assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(account2));

        // then
        assertEquals(1, userRepository.count());
    }

}