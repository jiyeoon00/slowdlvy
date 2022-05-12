package com.example.pilot.todo.application;

import com.example.pilot.todo.application.dto.TodoDtoAssembler;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.request.TodoUpdateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.application.dto.response.TodoDeleteResponseDto;
import com.example.pilot.todo.application.dto.response.TodoResponseDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;
import com.example.pilot.todo.domain.repository.TodoRepository;
import com.example.pilot.todo.exception.TodoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public void changeStatus(long todoId) {
        Todo todo = findTodoById(todoId);
        todo.changeStatus();
    }

    public TodoStatusChangeResponseDto changeAllTodoStatus(TodoStatus status) {
        long updateCount = todoRepository.updateAllStatus(status);
        return new TodoStatusChangeResponseDto(updateCount);
    }

    public void update(TodoUpdateRequestDto todoUpdateRequestDto) {
        Todo todo = findTodoById(todoUpdateRequestDto.getTodoId());
        todo.update(todoUpdateRequestDto.getText());
    }

    public void delete(long todoId) {
        todoRepository.delete(todoId);
    }

    public TodoDeleteResponseDto deleteComplete() {
        return new TodoDeleteResponseDto(todoRepository.deleteAllComplete());
    }

    public TodoResponseDto find(long todoId) {
        return TodoDtoAssembler.todoResponseDto(findTodoById(todoId));
    }

    private Todo findTodoById(long todoId) {
        return todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);
    }

    public List<TodoResponseDto> findTodoList(String status) {
        List<Todo> findTodoList = todoRepository.findAllByStatus(TodoStatus.convert(status));
        return findTodoList.stream()
                .map(TodoDtoAssembler::todoResponseDto)
                .collect(Collectors.toList());
    }
}
