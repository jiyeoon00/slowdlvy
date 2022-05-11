package com.hanium.pilot.response;

public class Response<T> {
    private final boolean success;
    private final int code;
    private final String message;
    private T data;

    Response(T data,ResponseState state){
        this.success=state.isSuccess();
        this.code=state.getCode();
        this.message=state.getMessage();
        this.data=data;
    }

    Response(ResponseState state){
        this.success=state.isSuccess();
        this.code=state.getCode();
        this.message=state.getMessage();
    }
}
