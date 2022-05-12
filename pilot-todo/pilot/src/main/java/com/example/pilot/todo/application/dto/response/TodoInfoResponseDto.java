package com.example.pilot.todo.application.dto.response;

import com.example.pilot.todo.domain.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TodoInfoResponseDto {
    private long id;
    private String text;
    private TodoStatus status;
    private LocalDateTime createdDate;
}
