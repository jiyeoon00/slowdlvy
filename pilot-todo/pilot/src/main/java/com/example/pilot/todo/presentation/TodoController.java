package com.example.pilot.todo.presentation;

import com.example.pilot.todo.application.TodoService;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.presentation.dto.TodoAssembler;
import com.example.pilot.todo.presentation.dto.request.TodoCreateRequest;
import com.example.pilot.todo.presentation.dto.request.TodoStatusChangeRequest;
import com.example.pilot.todo.presentation.dto.request.TodoUpdateRequest;
import com.example.pilot.todo.presentation.dto.response.TodoCreateResponse;
import com.example.pilot.todo.presentation.dto.response.TodoStatusChangeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}")
    public void todoUpdate(@PathVariable("id") long todoId, @RequestBody @Valid TodoUpdateRequest todoUpdateRequest) {
        todoService.update(TodoAssembler.todoUpdateRequestDto(todoId, todoUpdateRequest));
    }

    @PatchMapping("/{id}/status")
    public void todoStatusChange(@PathVariable("id") long todoId) {
        todoService.changeStatus(todoId);
    }

    @PatchMapping("/status")
    public TodoStatusChangeResponse allTodoStatusChange(@RequestBody TodoStatusChangeRequest todoStatusChangeRequest) {
        TodoStatusChangeResponseDto todoStatusChangeResponseDto = todoService.changeAllTodoStatus(todoStatusChangeRequest.getStatus());
        return TodoAssembler.todoStatusChangeResponse(todoStatusChangeResponseDto);
    }
}
