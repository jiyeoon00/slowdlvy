package com.example.pilot.todo.infrastructure;

import com.example.pilot.TestEntityFactory;
import com.example.pilot.config.jpa.JpaAuditingConfiguration;
import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@Import(JpaAuditingConfiguration.class)
@DataJpaTest
class TodoRepositoryTest {
    @Autowired private TodoDao todoDao;
    private TodoRepositoryImpl todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository = new TodoRepositoryImpl(todoDao);

        for (int i = 0; i < 10; i++) {
            Todo todo = TestEntityFactory.getTodoInstance();
            if(i % 2 == 0) todo.changeStatus();
            todoRepository.save(todo);
        }
    }

    @Test
    void 전체_완료_활성화_테스트() throws Exception{
        //given

        //when
        int count = todoRepository.updateAllStatus(TodoStatus.COMPLETE);

        //then
        assertThat(count).isEqualTo(10);

        List<Todo> todoList = todoDao.findAll();
        long completeCount = todoList.stream().filter(todo -> todo.getStatus() == TodoStatus.COMPLETE).count();
        assertThat(completeCount).isEqualTo(10);
    }


}