package com.example.pilot.todo.application;

import com.example.pilot.todo.application.dto.TodoDtoAssembler;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.repository.TodoRepository;
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
}
