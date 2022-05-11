package com.hanium.pilot.response;

import lombok.Getter;

@Getter
public enum ResponseState {

    OK(true,200,"요청성공");

    private final boolean success;
    private final int code;
    private final String message;

    ResponseState(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
