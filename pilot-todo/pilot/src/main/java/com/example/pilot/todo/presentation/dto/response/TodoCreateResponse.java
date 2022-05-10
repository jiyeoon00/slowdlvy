package com.example.pilot.todo.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TodoCreateResponse {
    private long id;
    private String text;
}
