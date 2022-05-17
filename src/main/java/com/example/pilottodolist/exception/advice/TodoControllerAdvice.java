package com.example.pilottodolist.exception.advice;

import com.example.pilottodolist.exception.BaseException;
import com.example.pilottodolist.exception.NotFoundException;
import com.example.pilottodolist.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class TodoControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponse TodoHandlerException(NotFoundException e) {

        log.error("TodoHandlerException : {} / {}", e.getCode().value(), e.getCode().getReasonPhrase());

        return ErrorResponse.builder()
                .code(e.getCode().value())
                .status(e.getCode().getReasonPhrase())
                .msg(e.getMsg())
                .build();
    }
}
