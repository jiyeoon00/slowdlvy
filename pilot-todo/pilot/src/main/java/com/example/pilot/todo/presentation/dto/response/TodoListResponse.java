package com.example.pilot.todo.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TodoListResponse {
    private List<TodoResponse> todoList;
    private int count;
}
