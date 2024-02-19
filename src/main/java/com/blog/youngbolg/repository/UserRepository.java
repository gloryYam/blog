package com.blog.youngbolg.repository;

import com.blog.youngbolg.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNickName(String nickName);

    Optional<Account> findByEmail(String email);
}
