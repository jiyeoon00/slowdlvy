package com.example.pilot.todo.domain;

import com.example.pilot.TestEntityFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TodoEntityTest {

    @Test
    void TodoStatus_변경_테스트() throws Exception{
        //given
        Todo todo = TestEntityFactory.getTodoInstance();
        TodoStatus beforeStatus = todo.getStatus();

        //when
        todo.changeStatus();
        TodoStatus afterStatus = todo.getStatus();

        //then
        assertThat(beforeStatus).isEqualTo(TodoStatus.ACTIVE);
        assertThat(afterStatus).isEqualTo(TodoStatus.COMPLETE);
    }
}