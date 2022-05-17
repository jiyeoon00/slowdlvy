package com.example.pilottodolist.service;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import com.example.pilottodolist.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;
    @InjectMocks
    TodoService todoService;

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateWithSleep(Long id) {
        todoService.changeProgress(id);
        sleep(1000);
    }

    @Test
    @DisplayName(value = "동시에 progress 상태 변경 테스트")
    void concurrentChangeProgressTest() throws InterruptedException {
        //given
        Todo todo = todoRepository.save(new Todo("Hello World"));
        Runnable userA = () -> {
            updateWithSleep(todo.getId());
        };

        Runnable userB = () -> {
            updateWithSleep(todo.getId());
        };

        Thread threadA = new Thread(userA);
        threadA.setName("***********Thread A************");
        Thread threadB = new Thread(userB);
        threadB.setName("***********Thread B************");

        //when
        threadA.start();
        threadB.start();

    }

    @Test
    @DisplayName(value = "progress 상태 수정 중일 때 삭제 테스트")
    void concurrentChangeAndDeleteProgressTest() {

    }
}