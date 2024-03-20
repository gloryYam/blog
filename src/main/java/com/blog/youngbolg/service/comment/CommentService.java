package com.blog.youngbolg.service.comment;

import com.blog.youngbolg.domain.Comment;
import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.exception.CommentNotFound;
import com.blog.youngbolg.exception.InvalidPassword;
import com.blog.youngbolg.exception.PostNotFound;
import com.blog.youngbolg.exception.UserNotFound;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.repository.comment.CommentRepository;
import com.blog.youngbolg.repository.post.PostRepository;
import com.blog.youngbolg.response.CommentResponse;
import com.blog.youngbolg.service.comment.request.CommentCreateServiceRequest;
import com.blog.youngbolg.service.comment.request.CommentDeleteServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse write(Long postId, CommentCreateServiceRequest request, Long userPrincipalId) {

        Post post = findPostById(postId);
        User user = findUserById(userPrincipalId);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Comment comment = Comment.of(user.getNickName(), request.getContent(), encryptedPassword, post);

        Comment saveComment = commentRepository.save(comment);
        return CommentResponse.of(saveComment);
    }

    @Transactional
    public void delete(Long id, CommentDeleteServiceRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFound::new);

        String encryptedPassword = comment.getPassword();
        if(!passwordEncoder.matches(request.getPassword(), encryptedPassword)) {
            throw new InvalidPassword();
        }

        commentRepository.delete(comment);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(UserNotFound::new);
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(PostNotFound::new);
    }
}
