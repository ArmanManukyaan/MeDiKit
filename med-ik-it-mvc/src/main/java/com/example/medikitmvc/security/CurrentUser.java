package com.example.medikitmvc.security;

import com.example.medikitcommon.entity.User;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;

@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),user.isEnabled(),
                true, true, true,
                AuthorityUtils.createAuthorityList(user.getUserType().name())
                );
        this.user = user;
    }

}
