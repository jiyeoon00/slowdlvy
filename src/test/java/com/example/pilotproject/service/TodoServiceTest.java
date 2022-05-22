package com.example.pilotproject.service;

import com.example.pilotproject.dto.CreateDto;
import com.example.pilotproject.dto.UpdateDto;
import com.example.pilotproject.entity.Todo;
import com.example.pilotproject.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    private TransactionTemplate transactionTemplate;

    @BeforeEach
     void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @DisplayName("낙관적 락 테스트")
    @Transactional
    @Test
    void 낙관적_락_테스트() throws Exception{
        // given
        int nThreads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads); // 스레드 3개 생성
        CountDownLatch countDownLatch = new CountDownLatch(nThreads);

        Todo before = transactionTemplate.execute((status) -> todoService.register(new CreateDto("before-content")));

        // when
        for(int i=0; i<nThreads; i++){
            executorService.execute(()->{
                Optional<Todo> testTodo = todoRepository.findById(before.getId());
                todoService.updateContent(testTodo.get().getId(), new UpdateDto("after"));
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        Thread.sleep(500);

        // then
        for(int i=0; i<nThreads; i++){
            /assertEquals(0, todoRepository.findById(before.getId()).get().getVersion());
        }
    }

}