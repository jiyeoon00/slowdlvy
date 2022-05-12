package com.example.pilot.common.exception.format;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    TODO_NOT_FOUND(NOT_FOUND, "해당 Todo를 찾을 수 없습니다."),
    INVALID_TODO_STATUS(BAD_REQUEST, "잘못된 Todo Status입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
