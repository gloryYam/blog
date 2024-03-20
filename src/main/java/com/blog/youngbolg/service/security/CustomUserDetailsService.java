package com.blog.youngbolg.service.security;

import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.domain.UserRole;
import com.blog.youngbolg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + "을 찾을 수 없습니다."));

        List<GrantedAuthority> roles = new ArrayList<>();

        if("dudrhkd4179@naver.com".equals(email)) {
            roles.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            roles.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new UserPrincipal(user, roles);
    }
}
