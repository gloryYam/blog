package com.blog.youngbolg.controller;

import com.blog.youngbolg.domain.Session;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.repository.SessionRepository;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.request.LoginReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void LoginReq() throws Exception {
        // given
        userRepository.save(User.builder()
                .name("글로리")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build());

        LoginReq LoginReq = com.blog.youngbolg.request.LoginReq.builder()
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(LoginReq);

        mockMvc.perform(post("/auth/LoginReq")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공 후 세선 1개 생성")
    void sessionCreate() throws Exception {
        // given
        userRepository.save(User.builder()
                .name("글로리")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build());

        LoginReq LoginReq = com.blog.youngbolg.request.LoginReq.builder()
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(LoginReq);

        mockMvc.perform(post("/auth/LoginReq")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        Assertions.assertEquals(1L, sessionRepository.count());
    }

    @Test
    @DisplayName("로그인 성공 후 세션 응답")
    void sessionResponse() throws Exception {
        // given
        userRepository.save(User.builder()
                .name("글로리")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build());

        LoginReq LoginReq = com.blog.youngbolg.request.LoginReq.builder()
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(LoginReq);

        mockMvc.perform(post("/auth/LoginReq")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andDo(print());

        Assertions.assertEquals(1L, sessionRepository.count());
    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지 접속한다 /glory")
    void afterLoginReqPermissionPage() throws Exception {
        // given
        User user = User.builder()
                .name("글로리")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        Session session = user.addSession();
        userRepository.save(user);

        mockMvc.perform(get("/glory")
                        .header("Authorization", session.getAccessToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입")
    void Signup() throws Exception {
        // given
        User user = User.builder()
                .name("글로리")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}