package com.example.pilot.todo.domain;

import com.example.pilot.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;
    private String text;
    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @Builder
    public Todo(Long id, String text) {
        this.id = id;
        this.text = text;
        this.status = TodoStatus.ACTIVE;
    }

    public void changeStatus() {
        this.status = status.toggle();
    }

    public void update(String text) {
        this.text = text;
    }
}
