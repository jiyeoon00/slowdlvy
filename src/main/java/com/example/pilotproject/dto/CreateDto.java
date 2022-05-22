package com.example.pilotproject.dto;

import com.example.pilotproject.entity.State;
import com.example.pilotproject.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateDto {
    private String content;

    public Todo toEntity(){
        return Todo.builder()
                .content(content)
                .state(State.ACTIVE)
                .build();
    }
}
