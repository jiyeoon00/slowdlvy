package com.example.pilot.todo.infrastructure;

import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final TodoDao todoDao;

    @Override
    public Todo save(Todo todo) {
        return todoDao.save(todo);
    }

    @Override
    public Optional<Todo> findById(long todoId) {
        return todoDao.findById(todoId);
    }
}
