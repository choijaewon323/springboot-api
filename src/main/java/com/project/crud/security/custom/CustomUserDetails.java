package com.project.crud.security.custom;

import com.project.crud.security.dto.UserTokenResponse;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
    private final transient UserTokenResponse userToken;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(final UserTokenResponse userToken, final Collection<? extends GrantedAuthority> authorities) {
        this.userToken = userToken;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userToken.getPassword();
    }

    @Override
    public String getUsername() {
        return userToken.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
