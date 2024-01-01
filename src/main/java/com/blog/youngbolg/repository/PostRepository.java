package com.blog.youngbolg.repository;

import com.blog.youngbolg.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
