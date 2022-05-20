package com.example.pilottodolist.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{
    public NotFoundException() {
        super(ErrorCode.TODO_NOT_FOUND);
    }
}
