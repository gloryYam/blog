package com.blog.youngbolg.repository;

import com.blog.youngbolg.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByEmailAndPassword(String email, String password);

    Optional<Account> findByEmail(String email);
}
