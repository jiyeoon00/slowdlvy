package com.example.pilot.todo.presentation.dto.response;

import com.example.pilot.todo.domain.TodoStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoInfoResponse {
    private long id;
    private String text;
    private TodoStatus status;
    private LocalDateTime createdDate;
}
