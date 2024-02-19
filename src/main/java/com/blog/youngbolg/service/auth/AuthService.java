package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.domain.Account;
import com.blog.youngbolg.exception.AlreadyExistsEmailException;
import com.blog.youngbolg.exception.AlreadyExistsNickNameException;
import com.blog.youngbolg.exception.UserNotFound;
import com.blog.youngbolg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이메일로 매칭되는 사용자를 가져온다. 없으면 예외
     * 있으면 평문으로 온 패스워드와 db에 저장된 패스워드 검증
     */

    public Long signup(Account account, String password2) {
        checkDuplicateEmail(account);
        checkDuplicateNickName(account);

        String encode = passwordEncoder.encode(account.getPassword());
        account.Password(encode);
        return userRepository.save(account).getId();
    }

    private void checkDuplicateNickName(Account account) {
        Optional<Account> userNickName = userRepository.findByNickName(account.getNickName());
        if(userNickName.isPresent()) {
            throw new AlreadyExistsNickNameException();
        }
    }

    private void checkDuplicateEmail(Account account) {
        Optional<Account> userEmail = userRepository.findByEmail(account.getEmail());
        if(userEmail.isPresent()) {
            throw new AlreadyExistsEmailException();
        }
    }

    public Account findOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFound::new);
    }
}
