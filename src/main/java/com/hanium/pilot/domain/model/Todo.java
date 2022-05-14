package com.hanium.pilot.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Todo extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private State state;

    public static Todo createTodo(String title){
        Todo todo = new Todo();
        todo.title = title;
        todo.state = State.ACTIVE;
        return todo;
    }


}
