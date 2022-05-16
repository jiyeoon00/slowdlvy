package com.example.pilot.todo.domain;

import com.example.pilot.common.exception.InvalidTodoStatusException;

import java.util.Arrays;
import java.util.Optional;

public enum TodoStatus {
    ACTIVE, COMPLETE;

    // String 타입 status를 TodoStatus 타입으로 변환
    public static TodoStatus convert(String status) {
        if(status == null) return null;
        return findTodoStatus(status);
    }

    private static TodoStatus findTodoStatus(String status) {
        Optional<TodoStatus> foundStatus = Arrays.stream(TodoStatus.values())
                .filter(todoStatus -> todoStatus.name().equals(status.toUpperCase()))
                .findAny();
        foundStatus.orElseThrow(InvalidTodoStatusException::new);
        return foundStatus.get();
    }

    public TodoStatus toggle() {
        return this == ACTIVE ? COMPLETE : ACTIVE;
    }
}
