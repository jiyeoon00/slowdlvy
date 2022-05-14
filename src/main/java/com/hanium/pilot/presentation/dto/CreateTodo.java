package com.hanium.pilot.presentation.dto;


import com.hanium.pilot.domain.model.Todo;
import lombok.Getter;
import lombok.Setter;

public class CreateTodo {
    @Setter
    @Getter
    public static class Request{
        String title;
    }
    @Getter
    @Setter
    public static class Response{
        Long id;
        String title;
        String state;

        public Response(Todo todo){
            id = todo.getId();
            title = todo.getTitle();
            state = todo.getState().toString();
        }
    }
}
