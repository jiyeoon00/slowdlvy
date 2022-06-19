package com.example.todoList.Dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class TodoRequest{
    @NotEmpty(message = "항목은 필수 입니다.")
    private String workTitle;
}
