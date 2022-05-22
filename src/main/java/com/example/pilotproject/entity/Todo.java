package com.example.pilotproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "todo")
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated
    private State state; // 완료 및 활성화

    @Version
    private Integer version;


    // update
    public void update(String content){
        this.content = content;
    }

    public void update(){
        this.state = this.state.equals(State.ACTIVE) ? State.COMPLETE : State.ACTIVE;
    }

}

