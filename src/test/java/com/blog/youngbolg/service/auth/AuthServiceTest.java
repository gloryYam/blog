package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.exception.AlreadyExistsEmailException;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.service.auth.request.SignupServiceRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입 성공")
    void successSignup() {
        // given
        SignupServiceRequest signupReq = SignupServiceRequest.builder()
            .name("김영광")
            .nickName("글로리")
            .email("test@naver.com")
            .password("1234")
            .build();

        // when
        authService.signup(signupReq);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();

        assertEquals("test@naver.com", user.getEmail());
        assertEquals("글로리", user.getNickName());
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
            .nickName("글로리")
            .email("dudrhkd4179@naver.com")
            .password("1234")
            .build();

        userRepository.save(user1);

        SignupServiceRequest request = SignupServiceRequest.builder()
            .name("test")
            .nickName("test")
            .email("dudrhkd4179@naver.com")
            .password("1234")
            .build();

        // when
        assertThatThrownBy(() -> authService.signup(request))
            .isInstanceOf(AlreadyExistsEmailException.class)
            .hasMessage("이미 가입된 이메일입니다.");

        // then
        assertThat(1).isEqualTo(userRepository.count());
    }

}