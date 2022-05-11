package com.example.pilot.todo.presentation.dto.request;

import com.example.pilot.todo.domain.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class TodoStatusChangeRequest {
    private TodoStatus status;
}
