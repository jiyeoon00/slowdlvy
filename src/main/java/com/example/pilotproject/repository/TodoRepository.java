package com.example.pilotproject.repository;

import com.example.pilotproject.entity.State;
import com.example.pilotproject.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findById(Long id);
    List<Todo> findALlByState(State state);
}
