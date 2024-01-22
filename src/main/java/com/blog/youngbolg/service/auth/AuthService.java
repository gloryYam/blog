package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.crypto.PasswordEncoder;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.exception.AlreadyExistsEmailException;
import com.blog.youngbolg.exception.InvalidSignInInformation;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.request.LoginReq;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
     * 이메일로 매칭되는 사용자를 가져온다. 없으면 예외
     * 있으면 평문으로 온 패스워드와 db에 저장된 패스워드 검증
     */
    @Transactional
    public Long signin(LoginReq loginReq) {
        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(InvalidSignInInformation::new);

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        boolean matches = passwordEncoder.matches(loginReq.getPassword(), user.getPassword());
        if(!matches) {
            throw new InvalidSignInInformation();
        }

//        Session session = user.addSession();
        return user.getId();
    }


    public Long signup(User user) {
        Optional<User> userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String encodedPassword = passwordEncoder.encrypt(user.getPassword());
        user.Password(encodedPassword);

        return userRepository.save(user).getId();
    }
}
