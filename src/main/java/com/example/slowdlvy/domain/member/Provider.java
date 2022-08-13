package com.example.slowdlvy.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    LOCAL("LOCAL","일반 로그인"),
    NAVER("NAVER","네이버 로그인회원"),
    GOOGLE("GOOGLE","구글 로그인회원");

    private final String key;
    private final String title;
}
