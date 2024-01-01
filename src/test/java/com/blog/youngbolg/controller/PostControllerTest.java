package com.blog.youngbolg.controller;

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 안녕을 출력한다")
    void test() throws Exception {
        // 글 제목
        // 글 내용

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}")
                ) // application/json
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print()); // 성공했을 때 http 요청에 대한 summary 를 남겨준게 된다
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다")
    void test2() throws Exception {
        // 글 제목
        // 글 내용

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
                ) // application/json
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation[0].fieldName").value("title"))
                .andExpect(jsonPath("$.validation[0].errorMessage").value("타이틀을 입력해주세요."))
                .andDo(print()); // 성공했을 때 http 요청에 대한 summary 를 남겨준게 된다
    }

    @Test
    @DisplayName("/posts 요청시 db에 값이 저장이 된다.")
    void test3() throws Exception {
        //when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다.\"}")
                ) // application/json
                .andExpect(status().isOk())
                .andDo(print()); // 성공했을 때 http 요청에 대한 summary 를 남겨준게 된다

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals(post.getTitle(), "제목입니다");
        assertEquals(post.getContent(), "내용입니다.");
    }
}