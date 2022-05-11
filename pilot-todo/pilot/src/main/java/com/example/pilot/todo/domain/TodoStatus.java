package com.example.pilot.todo.domain;

public enum TodoStatus {
    ACTIVE, COMPLETE;

    public TodoStatus toggle() {
        return this == ACTIVE ? COMPLETE : ACTIVE;
    }
}
