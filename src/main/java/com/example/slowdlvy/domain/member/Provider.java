package com.example.slowdlvy.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    LOCAL("LOCAL","일반 로그인");

    private final String key;
    private final String title;
}
