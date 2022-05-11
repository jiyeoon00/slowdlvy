package com.example.pilot.todo.application;

import com.example.pilot.todo.application.dto.TodoDtoAssembler;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;
import com.example.pilot.todo.domain.repository.TodoRepository;
import com.example.pilot.todo.exception.TodoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoCreateResponseDto save(TodoCreateRequestDto todoCreateRequestDto){
        Todo savedTodo = todoRepository.save(TodoDtoAssembler.toTodo(todoCreateRequestDto));
        return TodoDtoAssembler.todoCreateResponseDto(savedTodo);
    }

    public void changeStatus(long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);
        todo.changeStatus();
    }

    public TodoStatusChangeResponseDto changeAllTodoStatus(TodoStatus status) {
        int updateCount = todoRepository.updateAllStatus(status);
        return new TodoStatusChangeResponseDto(updateCount);
    }
}
