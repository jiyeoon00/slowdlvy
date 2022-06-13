package com.example.todoList.Dto;

import com.example.todoList.domain.Todo;
import com.example.todoList.domain.WorkStates;
import lombok.Data;

@Data
public class InfoTodoResponse {
    private Long id;
    private String workTitle;
    private WorkStates workStates;

    public InfoTodoResponse(Todo todo){
        id = todo.getId();
        workTitle = todo.getWorkTitle();
        workStates = todo.getStates();
    }

}
