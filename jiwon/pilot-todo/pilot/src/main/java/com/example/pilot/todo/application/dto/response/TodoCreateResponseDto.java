package com.example.pilot.todo.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TodoCreateResponseDto {
    private long id;
    private String text;
}
