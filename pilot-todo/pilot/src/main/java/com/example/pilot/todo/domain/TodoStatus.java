package com.example.pilot.todo.domain;

import com.example.pilot.common.exception.InvalidTodoStatusException;

import java.util.Arrays;
import java.util.Optional;

public enum TodoStatus {
    ACTIVE, COMPLETE;

    public static TodoStatus convert(String status) {
        if(status == null) return null;
        Optional<TodoStatus> optionalTodoStatus = Arrays.stream(TodoStatus.values())
                .filter(todoStatus -> todoStatus.name().equals(status.toUpperCase()))
                .findAny();
        optionalTodoStatus.orElseThrow(InvalidTodoStatusException::new);
        return optionalTodoStatus.get();
    }

    public TodoStatus toggle() {
        return this == ACTIVE ? COMPLETE : ACTIVE;
    }
}
