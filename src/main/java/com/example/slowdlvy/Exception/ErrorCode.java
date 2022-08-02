package com.example.slowdlvy.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 아이디입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
