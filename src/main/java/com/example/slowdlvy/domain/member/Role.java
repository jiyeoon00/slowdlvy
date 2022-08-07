package com.example.slowdlvy.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role implements GrantedAuthority {
    USER("ROLE_USER","일반 사용자"), ADMIN("ROLD_ADMIN","관리자");

    private final String authority;
    private final String title;

    public static Role valueOfCaseInsensitively(String authority) {
        return valueOf(StringUtils.uncapitalize(authority));
    }


    @Override
    public String getAuthority() {
        return authority;
    }
}
