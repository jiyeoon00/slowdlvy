package com.example.pilot.todo.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TodoInfoListResponse {
    private List<TodoInfoResponse> todoList;
    private int count;
}
