package com.example.todoList.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByStates(WorkStates states);

    void deleteByStates(WorkStates states);

    //state 전체 활성화 or 완료
    @Modifying(clearAutomatically = true)
    @Query("update Todo t set t.states = :newState where t.states = :oldState")
    int changeAllState(@Param("newState") WorkStates newState, @Param("oldState") WorkStates oldStates);
}
