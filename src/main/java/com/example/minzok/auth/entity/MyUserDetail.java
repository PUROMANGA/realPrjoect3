package com.example.minzok.auth.entity;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.enums.AddressType;
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
    private final String address;

    public MyUserDetail(Member member){
        this.member = member;
        this.address = member.getAddresses().stream()
                .filter(addr -> addr.getAddressType() == AddressType.DEFAULT)
                .findFirst()
                .map(Address::getAddressInfo)
                .orElse(null);
    }

    public LocalDate getBirthDate(){
        return member.getBirth();
    }

    public String getAddress(){
        return address;
    }

    public String getNickname(){
        return member.getNickname();
    }

    public String getName(){
        return member.getName();
    }

    public Long getMemberId(){
        return member.getId();
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
