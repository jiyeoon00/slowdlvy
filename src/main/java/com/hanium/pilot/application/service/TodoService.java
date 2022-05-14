package com.hanium.pilot.application.service;

import com.hanium.pilot.domain.model.Todo;
import com.hanium.pilot.infrastructure.repository.TodoRepository;
import com.hanium.pilot.infrastructure.store.TodoStore;
import com.hanium.pilot.presentation.dto.CreateTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoStore todoStore;

    @Transactional
    public CreateTodo.Response createTodo(CreateTodo.Request request){
        Todo todo = Todo.createTodo(request.getTitle());
        Todo savedTodo = todoStore.saveTodo(todo);
        return new CreateTodo.Response(savedTodo);
    }


}
