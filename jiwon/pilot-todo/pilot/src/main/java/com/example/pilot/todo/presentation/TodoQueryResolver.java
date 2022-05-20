package com.example.pilot.todo.presentation;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.pilot.todo.application.TodoService;
import com.example.pilot.todo.presentation.dto.TodoAssembler;
import com.example.pilot.todo.presentation.dto.response.TodoInfoListResponse;
import com.example.pilot.todo.presentation.dto.response.TodoInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class TodoQueryResolver implements GraphQLQueryResolver {
    private final TodoService todoService;

    public TodoInfoResponse findTodo(final long id) {
        return TodoAssembler.todoInfoResponse(todoService.find(id));
    }

    public TodoInfoListResponse findTodoList(String status) {
        return TodoAssembler.todoInfoListResponse(todoService.findTodoList(status));
    }
}
