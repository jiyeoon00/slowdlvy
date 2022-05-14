package com.hanium.pilot.presentation.dto;

import com.hanium.pilot.domain.model.Todo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponse {
    Long id;
    String title;
    String state;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.state = todo.getState().toString();
    }
}
