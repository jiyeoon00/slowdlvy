package com.hanium.pilot.infrastructure.repository;

import com.hanium.pilot.domain.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo,Long> {
}
