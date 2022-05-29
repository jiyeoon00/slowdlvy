package com.example.pilottodolist.domain;

import com.example.pilottodolist.exception.ErrorCode;
import com.example.pilottodolist.exception.dto.ErrorResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(value = EnumType.STRING)
    private Progress progress;
    private String content;

    @Version
    private Integer version;

    @Builder
    public Todo(String content) {
        this.content = content;
        this.progress = Progress.ACTIVE;
    }

    public void changeProgress() {
        this.progress = (this.progress == Progress.ACTIVE) ? Progress.COMPLETED : Progress.ACTIVE;
    }
}
