package com.example.pilot.common.exception;

import com.example.pilot.common.exception.format.ErrorCode;

public class TodoNotFoundException extends BusinessException {
    public TodoNotFoundException() {
        super(ErrorCode.TODO_NOT_FOUND);
    }
}
