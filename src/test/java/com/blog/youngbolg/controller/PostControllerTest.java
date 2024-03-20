package com.blog.youngbolg.controller;

import com.blog.youngbolg.config.YoungMockUser;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.repository.post.PostRepository;
import com.blog.youngbolg.request.post.PostCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clear() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @YoungMockUser
    @DisplayName("/posts 요청시 title 값은 필수다")
    void test1() throws Exception {
        PostCreateRequest request = PostCreateRequest.builder()
                .content("내용입니다.")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                ) // application/json
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validations[0].fieldName").value("title"))
                .andExpect(jsonPath("$.validations[0].errorMessage").value("타이틀을 입력해주세요."))
                .andDo(print()); // 성공했을 때 http 요청에 대한 summary 를 남겨준게 된다
    }

    @Test
    @YoungMockUser
    @DisplayName("글 작성")
    void test2() throws Exception {

        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);
        //when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test3() throws Exception {
        User user = User.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);
        // given
        Post post = Post.builder()
                .title("엔드")
                .content("백엔드")
                .account(user)
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("엔드"))
                .andExpect(jsonPath("$.content").value("백엔드"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러 개 조회")
    void test4() throws Exception {
        User user = User.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("블로그 제목 " + i)
                        .content("백엔드 " + i)
                        .account(user)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(11)))
                .andExpect(jsonPath("$.content[0].title").value("블로그 제목 19"))
                .andExpect(jsonPath("$.content[0].content").value("백엔드 19"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test5() throws Exception {
        User user = User.builder()
                .name("김영광")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);

        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("블로그 제목 " + i)
                        .content("백엔드 " + i)
                        .account(user)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/posts?page=1&size=11")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(11)))
                .andExpect(jsonPath("$.content[0].title").value("블로그 제목 19"))
                .andExpect(jsonPath("$.content[0].content").value("백엔드 19"))
                .andDo(print());
    }

    @Test
    @YoungMockUser
    @DisplayName("게시글 삭제")
    void test6() throws Exception {
        User user = userRepository.findAll().get(0);

        // given
        Post post = Post.builder()
                .title("glory")
                .content("안녕하세요")
                .account(user)
                .build();

        postRepository.save(post);

        // when
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        // then

    }

    @Test
    @DisplayName("존재하지 않은 게시글 조회")
    void test7() throws Exception {

        mockMvc.perform(get("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}








