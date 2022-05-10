package com.example.pilot.todo.presentation;

import com.example.pilot.todo.application.TodoService;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.presentation.dto.TodoAssembler;
import com.example.pilot.todo.presentation.dto.request.TodoCreateRequest;
import com.example.pilot.todo.presentation.dto.response.TodoCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/todo")
@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public TodoCreateResponse todoSave(@RequestBody @Valid TodoCreateRequest todoCreateRequest) {
        TodoCreateRequestDto todoCreateRequestDto = TodoAssembler.todoCreateRequestDto(todoCreateRequest);
        return TodoAssembler.todoCreateResponse(todoService.save(todoCreateRequestDto));
    }
}
