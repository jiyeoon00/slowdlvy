package com.example.pilot.todo.domain.repository;

import com.example.pilot.todo.domain.Todo;

import java.util.Optional;

public interface TodoRepository {
    Todo save(Todo todo);

    Optional<Todo> findById(long todoId);
}
