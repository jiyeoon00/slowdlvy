package com.example.todoList.domain;

public enum WorkStates {
    ACTIVE, COMPLETED;

    public static WorkStates makeWordStates(String status){
        return WorkStates.valueOf(status.toUpperCase());
    }
}
