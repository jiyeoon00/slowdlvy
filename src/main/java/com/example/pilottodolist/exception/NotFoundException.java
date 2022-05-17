package com.example.pilottodolist.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{
    public NotFoundException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "해당 Todo를 찾을 수 없습니다.");
    }
}
