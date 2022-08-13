package com.example.slowdlvy.security.oauth.provider;

import com.example.slowdlvy.domain.member.Provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public  NaverUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public Provider getProvider() {
        return Provider.NAVER;
    }

    @Override
    public String getUsername() {
        return getProviderId()+"_"+getEmail();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
