package ru.itis.activerewards.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.activerewards.models.User;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    static final long serialVersionUID = -9009109521886575334L;
    private User user;
    public UserDetailsImpl(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        return Collections.singleton(authority);
    }
    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }
    @Override
    public String getUsername() {
        return user.getLogin();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return !user.getState().equals(User.UserState.BANNED);
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return user.getState().equals(User.UserState.ACTIVE);
    }
    public User getUser() {
        return this.user;
    }
}
