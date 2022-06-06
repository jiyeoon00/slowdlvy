package com.example.todoList.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    OPTIMISTICLOCK(HttpStatus.BAD_REQUEST,"충돌이 감지되었습니다."),
    ///* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 TODO정보를 찾을 수 없습니다."),
    TODO_NOT_DELETE(HttpStatus.NOT_FOUND, "해당 TODO는 존재하지 않아 삭제할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
