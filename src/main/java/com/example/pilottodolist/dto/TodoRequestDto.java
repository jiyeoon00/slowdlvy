package com.example.pilottodolist.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class TodoRequestDto {

    @Data
    public static class CREATED {
        @NotBlank(message = "{dto.todo.NotBlank}")
        @Size(min = 1, max = 30, message = "{dto.todo.content.LimitSize}")
        private String content;
    }

    @Data
    public static class GET {
        @Pattern(regexp = "ACTIVE|COMPLETED", message = "{dto.progress.ActiveOrCompleted}")
        private String progress;
    }

    @Data
    public static class DELETED {

        @NotNull(message = "{dto.pk.NotNull}")
        private List<Long> ids;
    }

    @Data
    public static class ALLUPDATE {
        @NotBlank(message = "{dto.progress.NotBlank}")
        @Pattern(regexp = "ACTIVE|COMPLETED", message = "{dto.progress.ActiveOrCompleted}")
        private String currentState;
    }
}
