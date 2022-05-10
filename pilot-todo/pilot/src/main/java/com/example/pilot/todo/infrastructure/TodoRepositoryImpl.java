package com.example.pilot.todo.infrastructure;

import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.repository.TodoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepositoryImpl extends JpaRepository<Todo, Long>, TodoRepository {
}
