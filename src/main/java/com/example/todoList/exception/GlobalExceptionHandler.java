package com.example.todoList.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TodoNotFoundException.class)
    public ErrorResponse handleTodoNotFound(TodoNotFoundException ex){
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    //데이터에 존재하지 않는 todo 삭제 하려 할때 예외처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorResponse handleTodoNotFound(EmptyResultDataAccessException ex){
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "해당 todo를 찾을 수 없습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ErrorResponse handleOptimisticLock(ObjectOptimisticLockingFailureException ex){
        System.out.println("충돌감지");
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, "충돌감지");
    }
}
