package com.example.my_coffee_list.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.postingapp.entity.User;

public class UserDetailsImpl implements UserDetails {
    private final User user;
    private final Collection<GrantedAuthority> authorities;

    public UserDetailsImpl(User user){
        this.user = user;
    }

    @Override
    public User getUser(){
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public  String getusername(){
        return user.getEmail();
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
        return user.getEnabled();
    }
}
