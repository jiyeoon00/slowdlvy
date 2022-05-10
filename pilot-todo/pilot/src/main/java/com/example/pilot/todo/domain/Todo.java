package com.example.pilot.todo.domain;

import lombok.Getter;
import javax.persistence.*;

@Getter
@Entity
public class Todo extends BaseTimeEntity{
    @Id @GeneratedValue
    @Column(name = "todo_id")
    private Long id;
    private String text;
    @Enumerated(EnumType.STRING)
    private TodoStatus status;
}
