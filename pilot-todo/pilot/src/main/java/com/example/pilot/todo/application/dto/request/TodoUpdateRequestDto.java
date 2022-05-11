package com.example.pilot.todo.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TodoUpdateRequestDto {
    private long todoId;
    private String text;
}
