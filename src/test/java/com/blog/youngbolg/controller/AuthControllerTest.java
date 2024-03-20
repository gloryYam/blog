package com.blog.youngbolg.controller;

import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void Signup() throws Exception {
        // given
        User user = User.builder()
                .name("김영광")
                .nickName("글로리")
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