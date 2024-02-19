package com.blog.youngbolg.config.security;

import com.blog.youngbolg.domain.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Getter
public class UserPrincipal extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 166483891527373909L;
    private final Long userId;

    public UserPrincipal(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getEmail(), account.getPassword(), authorities);

        this.userId = account.getId();
    }
}
