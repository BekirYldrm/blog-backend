package com.pinsoft.intern.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    @Getter
    private final int id;
    private final String identifier;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(int id, String identifier, String password, String role) {
        this.id = id;
        this.identifier = identifier;
        this.password = password;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return identifier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isAdmin() {
        return getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    public boolean isUserSelf(int userId) {
        return getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")) && getId() == userId;
    }
    public boolean isAuthorSelf(int authorId) {
        return getAuthorities().contains(new SimpleGrantedAuthority("ROLE_AUTHOR")) && getId() == authorId;
    }
}
