package com.example.pilot;

import com.example.pilot.todo.domain.Todo;

import java.util.Random;

public class TestEntityFactory {

    public static Todo getTodoInstance() {
        return Todo.builder()
                .id(Math.abs(new Random().nextLong()))
                .text("test")
                .build();
    }
}
