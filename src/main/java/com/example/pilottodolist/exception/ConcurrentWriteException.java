package com.example.pilottodolist.exception;

public class ConcurrentWriteException extends BaseException{
    public ConcurrentWriteException() {
        super(ErrorCode.CONCURRENT_UPDATE_FAILURE);
    }
}
