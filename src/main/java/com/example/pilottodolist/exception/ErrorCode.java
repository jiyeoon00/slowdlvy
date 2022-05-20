package com.example.pilottodolist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 Todo를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String msg;
}
