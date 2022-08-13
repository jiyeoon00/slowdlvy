package com.example.slowdlvy.security.oauth.provider;

import com.example.slowdlvy.domain.member.Provider;

public interface OAuth2UserInfo {
    String getProviderId();
    Provider getProvider();
    String getUsername();
    String getEmail();
}
