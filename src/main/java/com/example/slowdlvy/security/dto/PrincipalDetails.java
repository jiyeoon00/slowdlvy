package com.example.slowdlvy.security.dto;

import com.example.slowdlvy.domain.member.Member;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    //일반로그인
    public PrincipalDetails(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.authorities = Collections.singletonList(member.getRole());
    }

    //OAuth2로그인
    public PrincipalDetails(Member member, Map<String, Object> attributes){
        this.id = member.getId();
        this.username = member.getUsername();
        this.authorities = Collections.singletonList(member.getRole());
        this.attributes = attributes;
    }

    public PrincipalDetails(String username, Collection<? extends GrantedAuthority> authorities){
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
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

    @Override
    public String getName() {
        return null;
    }
}
