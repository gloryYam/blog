package com.blog.youngbolg.controller;

import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.repository.SessionRepository;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.request.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    void login() throws Exception {
        // given
        userRepository.save(User.builder()
                .name("글로리")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(post("/auth/login")
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

        Login login = Login.builder()
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        Assertions.assertEquals(1L ,sessionRepository.count());
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

        Login login = Login.builder()
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(print());

        Assertions.assertEquals(1L ,sessionRepository.count());
    }
}