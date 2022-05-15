package com.hanium.pilot.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UpdateTodoStateRequest{
    @Min(value = 1)
    Long id;
    @Pattern(regexp = "ACTIVE|DELETED|COMPLETED")
    String state;
}
