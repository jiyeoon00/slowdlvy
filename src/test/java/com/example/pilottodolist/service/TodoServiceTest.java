package com.example.pilottodolist.service;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import com.example.pilottodolist.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class TodoServiceTest {

    @Autowired
    TodoService todoService;
    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName(value = "동시에 progress 상태 변경 테스트")
    void concurrentChangeProgressTest() throws InterruptedException {
        //given
        Todo todo = todoRepository.save(new Todo("Hello World", Progress.ACTIVE));
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        //when
        for(int i=0; i<2; i++) {
            executorService.execute(() -> {
                log.info("===============start===============");
                log.info("==============={}===============", todo.getVersion());
                todoService.changeProgress(todo.getId());
                latch.countDown();
                log.info("==============={}===============", todo.getVersion());
                log.info("===============end===============");
            });
        }

        latch.await();
        Thread.sleep(500);

        Todo result = todoRepository.findById(todo.getId()).orElseThrow();
        Assertions.assertThat(2).isEqualTo(result.getVersion());
    }

    @Test
    @DisplayName(value = "progress 상태 수정 중일 때 삭제 테스트")
    void concurrentChangeAndDeleteProgressTest() {

    }
}