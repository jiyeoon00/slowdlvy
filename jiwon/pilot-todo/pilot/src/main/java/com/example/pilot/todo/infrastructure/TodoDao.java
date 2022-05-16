package com.example.pilot.todo.infrastructure;

import com.example.pilot.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

interface TodoDao extends JpaRepository<Todo, Long> {
}
