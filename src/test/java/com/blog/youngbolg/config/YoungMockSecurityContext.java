package com.blog.youngbolg.config;

import com.blog.youngbolg.config.security.UserPrincipal;
import com.blog.youngbolg.domain.Account;
import com.blog.youngbolg.domain.AccountRole;
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
        Account account = Account.builder()
                .email(annotation.email())
                .nickName(annotation.nickName())
                .name(annotation.name())
                .password(annotation.password())
                .build();

        userRepository.save(account);

        List<SimpleGrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(AccountRole.ADMIN.getValue()));

        UserPrincipal userPrincipal = new UserPrincipal(account, role);

        var authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal,
                account.getPassword(),
                role);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);

        return context;
    }
}
