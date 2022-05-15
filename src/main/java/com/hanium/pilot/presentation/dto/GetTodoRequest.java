package com.hanium.pilot.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class GetTodoRequest {
    @Min(value = 1)
    Long id;
}
