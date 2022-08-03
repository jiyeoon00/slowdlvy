package com.example.slowdlvy.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "중복된 아이디입니다."),
    LOGIN_FAILURE(HttpStatus.BAD_REQUEST, "로그인 실패하였습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다.");


    private final HttpStatus httpStatus;
    private final String detail;
}