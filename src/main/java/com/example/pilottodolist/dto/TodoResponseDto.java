package com.example.pilottodolist.dto;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import lombok.Builder;
import lombok.Data;

public class TodoResponseDto {

    @Data
    public static class CREATED {
        private String content;
        private Progress progress;

        public CREATED(Todo todo) {
            this.content = todo.getContent();
            this.progress = todo.getProgress();
        }
    }

    @Data
    public static class GET {
        private Long id;
        private String content;
        private Progress progress;

        public GET(Todo todo) {
            this.id = todo.getId();
            this.content = todo.getContent();
            this.progress = todo.getProgress();
        }
    }

    @Data
    public static class ALLUPDATE {
        private String currentAllState;

        public ALLUPDATE(String currentAllState) {
            this.currentAllState = (currentAllState.equals("ACTIVE")) ? "COMPLETED" : "ACTIE";
        }
    }
}
