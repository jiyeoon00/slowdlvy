package com.example.pilot.todo.application;

import com.example.pilot.TestEntityFactory;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private TodoService todoService;
    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = TestEntityFactory.getTodoInstance();
    }

    @Test
    void 새로운_일정_저장_테스트() throws Exception{
        //given
        TodoCreateRequestDto todoCreateRequestDto = new TodoCreateRequestDto("test");
        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        //when
        TodoCreateResponseDto todoCreateResponseDto = todoService.save(todoCreateRequestDto);

        //then
        assertThat(todoCreateResponseDto.getId()).isGreaterThan(0L);
        assertThat(todoCreateResponseDto.getText()).isNotBlank();
    }
}