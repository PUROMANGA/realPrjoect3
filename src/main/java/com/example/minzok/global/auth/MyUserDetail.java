package com.example.minzok.global.auth;

import com.example.minzok.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class MyUserDetail implements UserDetails {

    private final Member member;

    public MyUserDetail(Member member) {
        this.member = member;
    }

    public LocalDate getBirthDate(){
        return member.getBirth();
    }

    public String getAddress(){
        return member.getAddress();
    }

    public String getNickname(){
        return member.getNickname();
    }

    public String getName(){
        return member.getName();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + member.getUserRole().name()));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
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
}
