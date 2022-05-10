package com.example.pilot.todo.domain;

import com.example.pilot.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Todo extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "todo_id")
    private Long id;
    private String text;
    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    public Todo(String text) {
        this.text = text;
        this.status = TodoStatus.ACTIVE;
    }

    @Builder
    public Todo(Long id, String text) {
        this.id = id;
        this.text = text;
        this.status = TodoStatus.ACTIVE;
    }
}
