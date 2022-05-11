package com.hanium.pilot.repository;

import com.hanium.pilot.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo,Long> {
}
