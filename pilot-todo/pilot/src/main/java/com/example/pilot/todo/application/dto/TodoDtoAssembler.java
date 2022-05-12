package com.example.pilot.todo.application.dto;

import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.application.dto.response.TodoInfoResponseDto;
import com.example.pilot.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoDtoAssembler {
    public static Todo toTodo(TodoCreateRequestDto todoCreateRequestDto) {
        return Todo.builder()
                .text(todoCreateRequestDto.getText())
                .build();
    }

    public static TodoCreateResponseDto todoCreateResponseDto(Todo todo) {
        return new TodoCreateResponseDto(todo.getId(), todo.getText());
    }

    public static TodoInfoResponseDto todoInfoResponseDto(Todo todo) {
        return TodoInfoResponseDto.builder()
                .id(todo.getId())
                .text(todo.getText())
                .status(todo.getStatus())
                .createdDate(todo.getCreatedDate())
                .build();
    }
}
