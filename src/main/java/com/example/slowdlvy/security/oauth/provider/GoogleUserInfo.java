package com.example.slowdlvy.security.oauth.provider;

import com.example.slowdlvy.domain.member.Provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public Provider getProvider() {
        return Provider.GOOGLE;
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
