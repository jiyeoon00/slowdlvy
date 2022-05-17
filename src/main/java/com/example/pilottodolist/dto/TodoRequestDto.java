package com.example.pilottodolist.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
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
    public static class DELETED {
        private List<Long> ids;
    }

    @Data
    public static class UPDATE {
        private Boolean isAllCheck;
    }
}
