package com.example.pilot.todo.infrastructure;

import com.example.pilot.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoDao extends JpaRepository<Todo, Long> {
    Optional<Todo> findById(long todoId);
}
