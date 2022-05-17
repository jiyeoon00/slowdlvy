package com.example.pilottodolist.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class TodoRequestDto {

    @Data
    public static class CREATED {
        @NotBlank(message = "할 일은 최소 1글자, 최대 30자 이내로 입력해주세요")
        @Size(min = 1, max = 30, message = "할 일은 최소 1글자, 최대 30자 이내로 입력해주세요")
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
