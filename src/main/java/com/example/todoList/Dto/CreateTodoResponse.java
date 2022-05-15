package com.example.todoList.Dto;

import lombok.Data;

@Data
public class CreateTodoResponse {
    private Long id;

    public CreateTodoResponse(Long id){
        this.id = id;
    }
}