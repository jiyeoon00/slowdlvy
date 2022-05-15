package com.example.todoList.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByStates(WorkStates states);

    void deleteByStates(WorkStates states);
}
