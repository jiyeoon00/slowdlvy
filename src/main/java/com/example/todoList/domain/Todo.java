package com.example.todoList.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Todo {

    @Id @GeneratedValue
    private Long id;

    private String workTitle;

    @Enumerated(EnumType.STRING)
    private WorkStates states;  //상태 [ACTIVE, COMPLETED]

    //생성자
    public Todo(String workTitle, WorkStates states){
        this.workTitle = workTitle;
        this.states = states;
    }

    public Todo() {

    }

    //==상태 변화 로직==//
    public void change(){
        if(states.equals(WorkStates.ACTIVE)){
            this.setStates(WorkStates.COMPLETED);
        }
        else
            this.setStates(WorkStates.ACTIVE);
    }
}
