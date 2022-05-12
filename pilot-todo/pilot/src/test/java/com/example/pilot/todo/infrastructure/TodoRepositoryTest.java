package com.example.pilot.todo.infrastructure;

import com.example.pilot.TestEntityFactory;
import com.example.pilot.config.jpa.JpaAuditingConfiguration;
import com.example.pilot.config.jpa.JpaQueryFactoryConfig;
import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@Import({JpaAuditingConfiguration.class, JpaQueryFactoryConfig.class})
@DataJpaTest
class TodoRepositoryTest {
    @Autowired private TodoDao todoDao;
    @Autowired private JPAQueryFactory queryFactory;
    @Autowired private EntityManager entityManager;
    private TodoRepositoryImpl todoRepository;

    @BeforeEach
    void setUp() {
        todoRepository = new TodoRepositoryImpl(todoDao, queryFactory);

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
        long count = todoRepository.updateAllStatus(TodoStatus.COMPLETE);

        //then
        assertThat(count).isEqualTo(10);

        entityManager.clear();
        entityManager.flush();

        List<Todo> todoList = todoDao.findAll();
        long completeCount = todoList.stream().filter(todo -> todo.getStatus() == TodoStatus.COMPLETE).count();
        assertThat(completeCount).isEqualTo(10);
    }

    @Test
    void 완료건_제거_테스트() throws Exception{
        //given
        List<Todo> todoList = todoDao.findAll();
        long completeTodoCount = todoList.stream().filter(todo -> todo.getStatus() == TodoStatus.COMPLETE).count();

        //when
        long deleteCount = todoRepository.deleteAllComplete();

        //then
        assertThat(deleteCount).isEqualTo(completeTodoCount);
    }

    @Test
    void 전체_Todo_목록_조회_테스트() throws Exception{
        //given

        //when
        List<Todo> todoList = todoRepository.findAllByStatus(null);
        long activeCount = todoList.stream().filter(todo -> todo.getStatus() == TodoStatus.ACTIVE).count();
        long completeCount = todoList.size() - activeCount;

        //then
        assertThat(todoList.size()).isEqualTo(10);
        assertThat(activeCount).isEqualTo(5);
        assertThat(completeCount).isEqualTo(5);
    }

    @Test
    void 활성화_Todo_목록_조회_테스트() throws Exception{
        //given

        //when
        List<Todo> todoList = todoRepository.findAllByStatus(TodoStatus.ACTIVE);
        long activeCount = todoList.stream().filter(todo -> todo.getStatus() == TodoStatus.ACTIVE).count();

        //then
        assertThat(todoList.size()).isEqualTo(5);
        assertThat(activeCount).isEqualTo(5);
    }

    @Test
    void 완료_Todo_목록_조회_테스트() throws Exception{
        //given

        //when
        List<Todo> todoList = todoRepository.findAllByStatus(TodoStatus.COMPLETE);
        long completeCount = todoList.stream().filter(todo -> todo.getStatus() == TodoStatus.COMPLETE).count();

        //then
        assertThat(todoList.size()).isEqualTo(5);
        assertThat(completeCount).isEqualTo(5);
    }
}