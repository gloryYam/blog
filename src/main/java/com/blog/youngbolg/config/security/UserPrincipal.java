package com.blog.youngbolg.config.security;

import com.blog.youngbolg.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Getter
public class UserPrincipal extends org.springframework.security.core.userdetails.User implements Serializable {

    @Serial
    private static final long serialVersionUID = 166483891527373909L;
    private final Long userId;

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);

        this.userId = user.getId();
    }
}
