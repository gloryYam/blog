package com.blog.youngbolg.repository.comment;

import com.blog.youngbolg.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
