package com.hanium.pilot.common.exception;

import graphql.kickstart.spring.error.ThrowableGraphQLError;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.OptimisticLockException;

@Component
public class ErrorHandler {
    @ExceptionHandler(RuntimeException.class)
    public ThrowableGraphQLError handle(RuntimeException e){
        return new ThrowableGraphQLError(e);
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ThrowableGraphQLError handle(OptimisticLockException e) {
        return new ThrowableGraphQLError(new Throwable("충돌입니다"));
    }

}
