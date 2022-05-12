package com.example.pilot.todo.domain;

import com.example.pilot.TestEntityFactory;
import com.example.pilot.common.exception.InvalidTodoStatusException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TodoEntityTest {

    @Test
    void TodoStatus_toggle_테스트() throws Exception{
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

    @Test
    void TodoStatus_convert_테스트() throws Exception{

        assertThat(TodoStatus.convert(null)).isNull();

        assertThat(TodoStatus.convert("active")).isEqualTo(TodoStatus.ACTIVE);
        assertThat(TodoStatus.convert("complete")).isEqualTo(TodoStatus.COMPLETE);

        assertThat(TodoStatus.convert("ACTIVE")).isEqualTo(TodoStatus.ACTIVE);
        assertThat(TodoStatus.convert("COMPLETE")).isEqualTo(TodoStatus.COMPLETE);

        assertThrows(InvalidTodoStatusException.class, () -> TodoStatus.convert("invalid"));
    }
}