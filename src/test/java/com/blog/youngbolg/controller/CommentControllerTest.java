package com.blog.youngbolg.controller;

import com.blog.youngbolg.config.YoungMockUser;
import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.domain.Account;
import com.blog.youngbolg.domain.Comment;
import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.repository.comment.CommentRepository;
import com.blog.youngbolg.repository.post.PostRepository;
import com.blog.youngbolg.request.Comment.CommentCreateReq;
import com.blog.youngbolg.request.Comment.CommentDeleteReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clear() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @YoungMockUser
    @DisplayName("댓글 작성")
    void test1() throws Exception {

        // given
        Account account = Account.builder()
                .name("김영광")
                .nickName("글로리")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();
        userRepository.save(account);

        // given
        Post post = Post.builder()
                .title("엔드")
                .content("백엔드")
                .account(account)
                .build();
        postRepository.save(post);
        // when

        CommentCreateReq request = CommentCreateReq.builder()
                .password("1234")
                .content("댓글입니다.ㅇㅇㅇㅇㅇㅇ")
                .build();

        String json = objectMapper.writeValueAsString(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        log.info("principalId={}", principal.getUserId());

        mockMvc.perform(post("/posts/{postId}/comments", post.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(1L, commentRepository.count());

        Comment comment = commentRepository.findAll().get(0);

        assertEquals("글로리", comment.getNickName());
        assertNotEquals("1234", comment.getPassword());
        assertTrue(passwordEncoder.matches("1234", comment.getPassword()));
        assertEquals("댓글입니다.ㅇㅇㅇㅇㅇㅇ", comment.getContent());
    }

    @Test
    @YoungMockUser
    @DisplayName("댓글 삭제")
    void delete() throws Exception {
        //given
        Account account = Account.builder()
                .name("김영광")
                .nickName("글로리")
                .email("dudrhkd4179@naver.com")
                .password("1234")
                .build();

        userRepository.save(account);

        Post post = Post.builder()
                .title("백엔드")
                .content("엔드엔드엔드엔드")
                .account(account)
                .build();

        postRepository.save(post);

        String encryptedPassword = passwordEncoder.encode("1234");

        Comment comment = Comment.builder()
                .content("아아아아아아ㅏㅏ아ㅏ아아아아ㅏㅇ")
                .nickName(account.getNickName())
                .build();
        comment.encodePassword(encryptedPassword);
        commentRepository.save(comment);

        CommentDeleteReq request = new CommentDeleteReq("1234");
        String json = objectMapper.writeValueAsString(request);
        //expected

        mockMvc.perform(post("/comments/{commentId}/delete", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}








