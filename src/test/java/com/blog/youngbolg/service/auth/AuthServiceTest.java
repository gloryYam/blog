package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.crypto.PasswordEncoder;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.exception.AlreadyExistsEmailException;
import com.blog.youngbolg.exception.InvalidSignInInformation;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.request.LoginReq;
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
        User signupReq = User.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        // when
        authService.signup(signupReq);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("dudrhkd4179@naver.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotEquals("1234", user.getPassword());
        assertEquals("김영광", user.getName());

    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void alreadyExistsEmail() {
        // given
        User user1 = User.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        userRepository.save(user1);

        User user2 = User.builder()
                .name("바나나")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        // when
        Assertions.assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(user2));

        // then
        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName("로그인 성공")
    void successLogin() {
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String passwordEncrypt = passwordEncoder.encrypt("1234");
        // given
        User signup = User.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password(passwordEncrypt)
                .build();

        authService.signup(signup);

        LoginReq login = LoginReq.builder()
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();
        // when
        Long userId = authService.signin(login);

        // then
        assertNotNull(userId);

    }

    @Test
    @DisplayName("비밀번호 틀림")
    void failLogin() {
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String passwordEncrypt = passwordEncoder.encrypt("1234");
        // given
        User signup = User.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password(passwordEncrypt)
                .build();

        authService.signup(signup);

        LoginReq login = LoginReq.builder()
                .email("dudrhkd4179@naver.com")
                .password("12345")
                .build();
        // when
        Assertions.assertThrows(InvalidSignInInformation.class, () -> authService.signin(login));

    }
}