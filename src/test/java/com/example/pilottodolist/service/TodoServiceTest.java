package com.example.pilottodolist.service;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import com.example.pilottodolist.dto.TodoRequestDto;
import com.example.pilottodolist.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoServiceTest {

    @Autowired
    private TodoRepository todoRepository;
    @SpyBean
    private TodoService todoService;

    @Test
    @DisplayName(value = "동시에 progress 상태 변경 테스트")
    void concurrentChangeProgressTest() throws InterruptedException {
        //given
        Todo todo = todoRepository.save(new Todo("Hello World"));
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        //when
        for (int i = 0; i<2; i++) {
            executor.execute(() -> todoService.changeProgress(todo.getId()));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        //then
        Todo afterTodo = todoRepository.findById(todo.getId()).get();
        assertThat(afterTodo.getVersion()).isEqualTo(1);
        assertThat(afterTodo.getProgress()).isEqualTo(Progress.COMPLETED);
    }
}