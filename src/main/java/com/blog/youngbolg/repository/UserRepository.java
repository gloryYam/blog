package com.blog.youngbolg.repository;

import com.blog.youngbolg.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName(String nickName);

    Optional<User> findByEmail(String email);
}
