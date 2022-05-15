package com.example.pilot.todo.domain.repository;

import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Todo save(Todo todo);

    Optional<Todo> findById(long todoId);

    long updateAllStatus(TodoStatus status);

    void delete(long todoId);

    long deleteAllComplete();

    List<Todo> findAllByStatus(TodoStatus status);

    Optional<Todo> findByIdForUpdate(long todoId);

}
