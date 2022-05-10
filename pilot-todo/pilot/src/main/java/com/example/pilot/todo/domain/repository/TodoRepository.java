package com.example.pilot.todo.domain.repository;

import com.example.pilot.todo.domain.Todo;

public interface TodoRepository {
    Todo save(Todo todo);
}
