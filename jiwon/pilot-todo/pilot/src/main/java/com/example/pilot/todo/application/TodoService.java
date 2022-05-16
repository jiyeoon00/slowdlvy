package com.example.pilot.todo.application;

import com.example.pilot.common.exception.TodoNotFoundException;
import com.example.pilot.todo.application.dto.TodoDtoAssembler;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.request.TodoUpdateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.application.dto.response.TodoDeleteResponseDto;
import com.example.pilot.todo.application.dto.response.TodoInfoResponseDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;
import com.example.pilot.todo.domain.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoCreateResponseDto save(TodoCreateRequestDto todoCreateRequestDto){
        Todo savedTodo = todoRepository.save(TodoDtoAssembler.toTodo(todoCreateRequestDto));
        return TodoDtoAssembler.todoCreateResponseDto(savedTodo);
    }

    // ACTIVE <-> COMPLETE
    public void toggleStatus(long todoId) {
        Todo todo = findByIdForUpdate(todoId);
        todo.changeStatus();
    }

    public TodoStatusChangeResponseDto changeAllTodoStatus(TodoStatus status) {
        long updateCount = todoRepository.updateAllStatus(status);
        return new TodoStatusChangeResponseDto(updateCount);
    }

    public void update(TodoUpdateRequestDto todoUpdateRequestDto) {
        Todo todo = findByIdForUpdate(todoUpdateRequestDto.getTodoId());
        todo.update(todoUpdateRequestDto.getText());
    }

    public void delete(long todoId) {
        todoRepository.delete(todoId);
    }

    public TodoDeleteResponseDto deleteAllCompleteTodo() {
        return new TodoDeleteResponseDto(todoRepository.deleteAllComplete());
    }

    @Transactional(readOnly = true)
    public TodoInfoResponseDto find(long todoId) {
        return TodoDtoAssembler.todoInfoResponseDto(findTodoById(todoId));
    }

    @Transactional(readOnly = true)
    public List<TodoInfoResponseDto> findTodoList(String status) {
        List<Todo> foundTodoList = todoRepository.findAllByStatus(TodoStatus.convert(status));
        return foundTodoList.stream()
                .map(TodoDtoAssembler::todoInfoResponseDto)
                .collect(Collectors.toList());
    }

    private Todo findTodoById(long todoId) {
        return todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);
    }

    private Todo findByIdForUpdate(long todoId) {
        return todoRepository.findByIdForUpdate(todoId).orElseThrow(TodoNotFoundException::new);
    }
}
