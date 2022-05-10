package com.example.pilottodolist.dto;

import lombok.Data;

import java.util.List;

@Data
public class TodoListInfo<T> {

    private int count;
    private List<T> todoList;

    public TodoListInfo(int count, List<T> todoList) {
        this.count = count;
        this.todoList = todoList;
    }
}
