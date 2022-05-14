package com.hanium.pilot.application.service;

import com.hanium.pilot.domain.model.Todo;
import com.hanium.pilot.infrastructure.store.TodoStore;

import com.hanium.pilot.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoStore todoStore;

    @Transactional
    public TodoResponse createTodo(CreateTodoRequest request){
        Todo todo = Todo.createTodo(request.getTitle());
        Todo savedTodo = todoStore.saveTodo(todo);
        return new TodoResponse(savedTodo);
    }

    @Transactional
    public TodoResponse updateTodoState(UpdateTodoStateRequest request) {
        Todo todo = todoStore.getExistTodo(request.getId());
        Todo updatedTodo = todoStore.updateTodoState(todo,request.getState());
        return new TodoResponse(updatedTodo);
    }

    @Transactional
    public TodoResponse updateTodoTitle(UpdateTodoTitleRequest request) {
        Todo todo = todoStore.getExistTodo(request.getId());
        Todo updatedTodo = todoStore.updateTodoTitle(todo,request.getTitle());
        return new TodoResponse(updatedTodo);
    }

    @Transactional
    public List<TodoResponse> updateAllTodoState(UpdateAllTodoStateRequest request) {
        List<Todo> todoList = todoStore.getExistAllTodo();
        List<Todo> updatedTodoList = todoStore.updateAllTodoState(request.getState(),todoList);
        return updatedTodoList.stream()
                .map(t->new TodoResponse(t))
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoResponse getTodo(GetTodoRequest request) {
        Todo todo = todoStore.getExistTodo(request.getId());
        return new TodoResponse(todo);
    }

    @Transactional
    public List<TodoResponse> getAllTodo() {
        List<Todo> todoList = todoStore.getExistAllTodo();
        return todoList.stream()
                .map(t->new TodoResponse(t))
                .collect(Collectors.toList());
    }
}
