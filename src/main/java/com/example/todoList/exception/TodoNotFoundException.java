package com.example.todoList.exception;

public class TodoNotFoundException extends RuntimeException{
    private static final String MESSAGE = "해당 todo를 찾을 수 없습니다.";
    public TodoNotFoundException(){
        super(MESSAGE);
    }
}
