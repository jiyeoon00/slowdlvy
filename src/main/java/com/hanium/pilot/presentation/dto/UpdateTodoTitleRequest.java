package com.hanium.pilot.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTodoTitleRequest {
    Long id;
    String title;
}
