package com.blog.youngbolg.service.comment;

import com.blog.youngbolg.domain.Comment;
import com.blog.youngbolg.domain.Post;
import com.blog.youngbolg.exception.CommentNotFound;
import com.blog.youngbolg.exception.InvalidPassword;
import com.blog.youngbolg.exception.PostNotFound;
import com.blog.youngbolg.repository.comment.CommentRepository;
import com.blog.youngbolg.repository.post.PostRepository;
import com.blog.youngbolg.request.Comment.CommentDeleteReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public Long write(Comment comment, String password) {
        passwordEncoder(comment, password);
        return commentRepository.save(comment).getId();
    }

    public void delete(Long id, CommentDeleteReq request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFound::new);

        String encryptedPassword = comment.getPassword();
        if(!passwordEncoder.matches(request.getPassword(), encryptedPassword)) {
            throw new InvalidPassword();
        }

        commentRepository.delete(comment);
    }

    private void passwordEncoder(Comment comment, String password) {
        String encode = passwordEncoder.encode(password);
        comment.encodePassword(encode);
    }

    public Post postFindOne(Long id) {
        return postRepository.findById(id)
                .orElseThrow(PostNotFound::new);
    }

    public Comment commentFindOne(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(CommentNotFound::new);
    }
}
