package com.example.todoList.Service;

import com.example.todoList.domain.Todo;
import com.example.todoList.domain.TodoRepository;
import com.example.todoList.domain.WorkStates;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceTest {

    @Autowired TodoService todoService;
    @Autowired TodoRepository todoRepository;
    @Autowired private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }


    @Test
    @Transactional
    public void TODO추가() throws Exception{
        //given
        Todo todo = new Todo("과제하기", WorkStates.ACTIVE);
        //when
        todoService.add(todo);
        Long savedId = todoService.add(todo);
        //then
        assertEquals(todo.getWorkTitle(), todoRepository.findById(savedId).get().getWorkTitle());
        assertEquals(todo.getStates(), todoRepository.findById(savedId).get().getStates());
    }

    @Test
    public void 동시수정테스트() throws Exception{
        //given
        final int numberOfThreads = 3;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        Todo todo = new Todo("과제하기", WorkStates.ACTIVE);
        Long savedId = transactionTemplate.execute((status) -> todoService.add(todo));
        //when
        for (int i=0; i< numberOfThreads; i++){
                executorService.execute(() -> {
                    transactionTemplate.execute(status -> {
                        todoService.Update(savedId, "테스트");
                        latch.countDown();
                        return null;
                    });
                });

        }
        latch.await();
        //then
        Todo afterTodo = todoRepository.findById(savedId).get();
        assertEquals("수정 되었는지 확인","테스트",afterTodo.getWorkTitle());
        assertEquals("버전확인",1,afterTodo.getVersion());
    }




}