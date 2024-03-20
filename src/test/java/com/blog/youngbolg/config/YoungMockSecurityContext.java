package com.blog.youngbolg.config;

import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.domain.User;
import com.blog.youngbolg.domain.UserRole;
import com.blog.youngbolg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class YoungMockSecurityContext implements WithSecurityContextFactory<YoungMockUser> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(YoungMockUser annotation) {
        User user = User.builder()
                .email(annotation.email())
                .nickName(annotation.nickName())
                .name(annotation.name())
                .password(annotation.password())
                .build();

        userRepository.save(user);

        List<SimpleGrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));

        UserPrincipal userPrincipal = new UserPrincipal(user, role);

        var authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal,
                user.getPassword(),
                role);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}
