package com.blog.youngbolg.service.auth;

import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.exception.AlreadyExistsEmailException;
import com.blog.youngbolg.repository.UserRepository;
import com.blog.youngbolg.response.SignResponse;
import com.blog.youngbolg.service.auth.request.SignupServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이메일로 매칭되는 사용자를 가져온다. 없으면 예외
     * 있으면 평문으로 온 패스워드와 db에 저장된 패스워드 검증
     */

    public SignResponse signup(SignupServiceRequest request) {
        User user = request.toEntity();

        checkDuplicateEmail(user);
        checkDuplicateNickName(user);

        String encode = passwordEncoder.encode(user.getPassword());
        user.Password(encode);
        User saveUser = userRepository.save(user);

        return SignResponse.of(saveUser);
    }

    public User findUserById(Long authId) {
        return userRepository.findById(authId)
            .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));
    }

    private void checkDuplicateEmail(User user) {
        userRepository.findByEmail(user.getEmail())
            .ifPresent(u -> {
                throw new AlreadyExistsEmailException();
            });

    }

    private void checkDuplicateNickName(User user) {
        userRepository.findByNickName(user.getNickName())
            .ifPresent(u -> {
                throw new AlreadyExistsEmailException();
            });
    }
}
