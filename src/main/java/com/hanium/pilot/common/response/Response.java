package com.hanium.pilot.common.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response<T> {
    private final Boolean success;
    private final Integer code;
    private final String message;
    private T data;

    public Response(T data,ResponseState state){
        this.success=state.isSuccess();
        this.code=state.getCode();
        this.message=state.getMessage();
        this.data=data;
    }

    public Response(ResponseState state){
        this.success=state.isSuccess();
        this.code=state.getCode();
        this.message=state.getMessage();
    }
}
