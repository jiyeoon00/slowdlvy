package com.example.pilot.todo.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoCreateRequestDto {
    private String text;
}
