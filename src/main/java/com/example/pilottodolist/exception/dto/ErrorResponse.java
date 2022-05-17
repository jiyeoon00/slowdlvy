package com.example.pilottodolist.exception.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ErrorResponse {

    private final String status;
    private final int code;
    private final String msg;
}
