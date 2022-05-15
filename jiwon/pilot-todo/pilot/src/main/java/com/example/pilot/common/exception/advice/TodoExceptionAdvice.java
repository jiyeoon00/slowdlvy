package com.example.pilot.common.exception.advice;

import com.example.pilot.common.exception.BusinessException;
import com.example.pilot.common.exception.format.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class TodoExceptionAdvice {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.info("[handleBusinessException] : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }


}
