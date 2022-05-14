package com.hanium.pilot.infrastructure.repository;

import com.hanium.pilot.domain.model.State;
import com.hanium.pilot.domain.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TodoRepository extends JpaRepository<Todo,Long> {

    @Query("select t from Todo t where t.id=:id and t.state<>:state")
    Optional<Todo> findExistTodoById(@Param("id") Long id,
                                     @Param("state") State state);

    @Query("select t from Todo t where t.state<>:state")
    List<Todo> findExistAllTodo(@Param("state") State state);
}
