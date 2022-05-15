package com.hanium.pilot.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateTodoTitleRequest {
    @Min(value = 1)
    Long id;
    @NotBlank
    String title;
}
