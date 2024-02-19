package com.blog.youngbolg.repository;

import com.blog.youngbolg.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmailAndPassword(String email, String password);

    Optional<Account> findByEmail(String email);
}
