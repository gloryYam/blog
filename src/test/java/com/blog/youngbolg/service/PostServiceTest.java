package com.blog.youngbolg.service;

import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.exception.PostNotFound;
import com.blog.youngbolg.repository.PostRepository;
import com.blog.youngbolg.request.PostCreateReq;
import com.blog.youngbolg.request.PostEditReq;
import com.blog.youngbolg.request.PostSearchReq;
import com.blog.youngbolg.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clear() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreateReq postCreateReq = PostCreateReq.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreateReq);

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals(post.getTitle(), "제목입니다.");
        assertEquals(post.getContent(), "내용입니다.");
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {

        //given
        Long postId = 1L;
        Post requestPost = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postRepository.save(requestPost);

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("제목입니다.", response.getTitle());
        assertEquals("내용입니다.", response.getContent());
    }
    
    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {

        //given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("블로그 제목 " + i)
                        .content("백엔드 " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearchReq postSearchReq = PostSearchReq.builder()
                .page(1)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearchReq);

        // then
        assertEquals(10L, posts.size());
        assertEquals("블로그 제목 19", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {

        //given
        Post post = Post.builder()
                .title("김영광")
                .content("안녕하세요")
                .build();

        postRepository.save(post);

        PostEditReq postEdit = PostEditReq.builder()
                .title("백엔드")
                .content("안녕하세요")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id +" + post.getId()));

        assertEquals("백엔드", changePost.getTitle());
        assertEquals("안녕하세요", changePost.getContent());
    }

    /*@Test
    @DisplayName("글 내용 수정")
    void test5() {

        //given
        Post post = Post.builder()
                .title("김영광")
                .content("안녕하세요")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("반갑습니다.")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id +" + post.getId()));

        assertEquals("김영광", changePost.getTitle());
        assertEquals("반갑습니다.", changePost.getContent());
    }*/

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        // given
        Post post = Post.builder()
                .title("김영광")
                .content("안녕하세요")
                .build();

        postRepository.save(post);

        // when
        postService.delete(post.getId());

        // then
        assertEquals(0, postRepository.count());

    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않은 글")
    void test7() {

        //given
        Long postId = 1L;
        Post post = Post.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });

    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않은 글 ")
    void test8() {
        // given
        Post post = Post.builder()
                .title("김영광")
                .content("안녕하세요")
                .build();

        postRepository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });

    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않은 글입니다.")
    void test9() {

        //given
        Post post = Post.builder()
                .title("김영광")
                .content("안녕하세요")
                .build();

        postRepository.save(post);

        PostEditReq postEdit = PostEditReq.builder()
                .title(null)
                .content("반갑습니다.")
                .build();

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
    }

}