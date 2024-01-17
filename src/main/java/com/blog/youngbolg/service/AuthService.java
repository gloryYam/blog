package com.blog.youngbolg.service;

import com.blog.youngbolg.domain.Session;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.exception.InvalidSignInInformation;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.request.Login;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String sign(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSignInInformation::new);

        Session session = user.addSession();
        return session.getAccessToken();
    }

}
