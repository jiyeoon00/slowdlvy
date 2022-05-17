package com.example.pilottodolist.dto;

import com.example.pilottodolist.domain.Todo;

public class DtoFormatter {

    public static Todo CreateRequestToTodo(TodoRequestDto.CREATED request) {
        return Todo.builder()
                .content(request.getContent())
                .build();
    }
}
