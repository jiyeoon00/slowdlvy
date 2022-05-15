package com.example.pilot.common.exception;

import com.example.pilot.common.exception.format.ErrorCode;

public class InvalidTodoStatusException extends BusinessException{
    public InvalidTodoStatusException() {
        super(ErrorCode.INVALID_TODO_STATUS);
    }
}
