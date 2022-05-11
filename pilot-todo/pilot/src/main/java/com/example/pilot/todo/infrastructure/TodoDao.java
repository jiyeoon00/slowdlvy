package com.example.pilot.todo.infrastructure;

import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoDao extends JpaRepository<Todo, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Todo t set t.status = :status")
    int updateAllStatus(@Param("status") TodoStatus status);
}
