package com.example.pilottodolist.dto;

import lombok.Data;

import java.util.List;

public class TodoRequestDto {

    @Data
    public static class CREATED {
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
