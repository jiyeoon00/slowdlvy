package com.example.slowdlvy.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TokenInfo {

    private String accessToken;
    private String refreshToken;
}
