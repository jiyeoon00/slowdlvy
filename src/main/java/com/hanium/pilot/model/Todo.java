package com.hanium.pilot.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Todo extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String todo;

    @Enumerated(EnumType.STRING)
    private State state;



}
