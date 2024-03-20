package com.blog.youngbolg.service.comment;

import com.blog.youngbolg.config.YoungMockUser;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.domain.Comment;
import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.exception.CommentNotFound;
import com.blog.youngbolg.exception.InvalidPassword;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.repository.comment.CommentRepository;
import com.blog.youngbolg.repository.post.PostRepository;
import com.blog.youngbolg.response.CommentResponse;
import com.blog.youngbolg.service.comment.request.CommentCreateServiceRequest;
import com.blog.youngbolg.service.comment.request.CommentDeleteServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @YoungMockUser
    @DisplayName("댓글 등록")
    void write() {
        // given
        User user = User.builder()
            .name("glory")
            .email("test@gmail.com")
            .password("1234")
            .nickName("test")
            .build();
        userRepository.save(user);

        Post post = Post.builder()
            .title("백엔드")
            .content("내용입니다.")
            .build();
        postRepository.save(post);


        CommentCreateServiceRequest request = CommentCreateServiceRequest.builder()
            .content("test")
            .password("1234")
            .build();

        // when
        CommentResponse commentResponse = commentService.write(post.getId(), request, user.getId());

        // then
        assertThat(commentResponse.getContent()).isEqualTo("test");
        assertThat(passwordEncoder.matches("1234", commentResponse.getPassword())).isTrue();
    }

    @Test
    @YoungMockUser
    @DisplayName("댓글 삭제하기")
    void delete() {
        // given
        User user = User.builder()
            .name("glory")
            .email("test@gmail.com")
            .password("1234")
            .nickName("test")
            .build();
        userRepository.save(user);

        Post post = Post.builder()
            .title("백엔드")
            .content("내용입니다.")
            .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
            .content("test")
            .password(passwordEncoder.encode("1234"))
            .nickName(user.getNickName())
            .post(post)
            .build();

        commentRepository.save(comment);

        CommentDeleteServiceRequest request = CommentDeleteServiceRequest.builder()
            .password("1234")
            .build();

        // when
        commentService.delete(comment.getId(), request);

        // then
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(0);

        Optional<Comment> deletedComment = commentRepository.findById(comment.getId());
        assertThat(deletedComment).isEmpty();
    }

    @Test
    @YoungMockUser
    @DisplayName("잘못된 비밀번호로 작성했을 때 예외가 발생하는지 확인")
    void wrongPasswordException() {
        // given
        User user = User.builder()
            .name("glory")
            .email("test@gmail.com")
            .password("1234")
            .nickName("test")
            .build();
        userRepository.save(user);

        Post post = Post.builder()
            .title("백엔드")
            .content("내용입니다.")
            .build();
        postRepository.save(post);

        Comment comment = Comment.builder()
            .content("test")
            .password(passwordEncoder.encode("1234"))
            .nickName(user.getNickName())
            .post(post)
            .build();

        commentRepository.save(comment);

        CommentDeleteServiceRequest request = CommentDeleteServiceRequest.builder()
            .password("1233")
            .build();

        // when
        assertThatThrownBy(() -> commentService.delete(comment.getId(), request))
            .isInstanceOf(InvalidPassword.class)
            .hasMessage("비밀번호가 올바르지 않습니다.");
    }

    @Test
    @YoungMockUser
    @DisplayName("존재하지 않은 ID로 삭제를 했을 때 예외가 발생하는지 확인")
    void nonExistIdDelete() {
        // given
        User user = User.builder()
            .name("glory")
            .email("test@gmail.com")
            .password("1234")
            .nickName("test")
            .build();
        userRepository.save(user);

        Post post = Post.builder()
            .title("백엔드")
            .content("내용입니다.")
            .build();
        postRepository.save(post);

        Long CommentId = 2L;

        CommentDeleteServiceRequest request = CommentDeleteServiceRequest.builder()
            .password("1234")
            .build();

        // when //then
        assertThatThrownBy(() -> commentService.delete(CommentId, request))
            .isInstanceOf(CommentNotFound.class)
            .hasMessage("존재하지 않은 댓글입니다.");
    }
}