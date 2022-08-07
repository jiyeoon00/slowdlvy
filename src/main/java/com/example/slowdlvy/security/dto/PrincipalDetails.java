package com.example.slowdlvy.security.dto;

import com.example.slowdlvy.domain.member.Member;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PrincipalDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public PrincipalDetails(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.authorities = Collections.singletonList(member.getRole());
    }

    public PrincipalDetails(String username, Collection<? extends GrantedAuthority> authorities){
        this.id = null;
        this.username = username;
        this.password = null;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
