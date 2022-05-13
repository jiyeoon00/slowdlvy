package com.example.pilot.todo.presentation.dto.request;

import com.example.pilot.todo.domain.TodoStatus;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoStatusChangeRequest {
    private TodoStatus status;
}
