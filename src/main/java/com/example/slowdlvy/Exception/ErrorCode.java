package com.example.slowdlvy.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERNAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),
    LOGIN_FAILURE(HttpStatus.BAD_REQUEST, "로그인 실패하였습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다."),
    INVAILD_REFRESH_TOKEN(HttpStatus.NOT_FOUND,"유효하지 않은 REFRESHTOKEN입니다."),
    PROVIDER_TYPE_ERROR(HttpStatus.BAD_REQUEST,"구글과 네이버만 지원합니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
