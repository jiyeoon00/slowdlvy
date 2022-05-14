package com.hanium.pilot.infrastructure.store;

import com.hanium.pilot.domain.model.Todo;
import com.hanium.pilot.infrastructure.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodoStore {
    private final TodoRepository todoRepository;

    public Todo saveTodo(Todo todo){
        return todoRepository.save(todo);
    }
}
