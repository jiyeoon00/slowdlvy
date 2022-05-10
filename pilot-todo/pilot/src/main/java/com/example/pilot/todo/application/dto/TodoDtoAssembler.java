package com.example.pilot.todo.application.dto;

import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoDtoAssembler {
    public static Todo toTodo(TodoCreateRequestDto todoCreateRequestDto) {
        return new Todo(todoCreateRequestDto.getText());
    }

    public static TodoCreateResponseDto todoCreateResponseDto(Todo todo) {
        return new TodoCreateResponseDto(todo.getId(), todo.getText());
    }
}
