package com.example.pilottodolist.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(value = EnumType.STRING)
    private Progress progress;
    private String content;

    @Version
    private Integer version;

    @Builder
    public Todo(String content, Progress progress) {
        this.content = content;
        this.progress = progress;
    }

    public void changeProgress() {
        this.progress = (this.progress == Progress.ACTIVE) ? Progress.COMPLETED : Progress.ACTIVE;
    }
}
