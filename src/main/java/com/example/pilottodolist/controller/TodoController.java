package com.example.pilottodolist.controller;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.dto.TodoListInfo;
import com.example.pilottodolist.dto.TodoRequestDto;
import com.example.pilottodolist.dto.TodoResponseDto;
import com.example.pilottodolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 할일 추가
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/todo")
    public TodoResponseDto.CREATED createTodo(@RequestBody @Valid TodoRequestDto.CREATED created) {
        return todoService.create(created);
    }

    /**
     * 상태 수정
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{todoId}/progress")
    public void changeProgress(@PathVariable("todoId") Long id) {
        todoService.changeProgress(id);
    }

    /**
     * 체크(COMPLETED)한 할일 삭제
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/delete-todos")
    public void deleteTodoList(@RequestBody @Valid TodoRequestDto.DELETED deleted) {
        todoService.deleteTodoList(deleted);
    }

    /**
     * 전체 조회
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/todoList")
    public TodoListInfo<TodoResponseDto.GET> getTodoList(@Valid TodoRequestDto.GET request) {
        List<TodoResponseDto.GET> todoList = todoService.getTodoList(Progress.of(request.getProgress()))
                .stream().map(t -> new TodoResponseDto.GET(t)).collect(Collectors.toList());
        return new TodoListInfo<>(todoList.size(), todoList);
    }

    /**
     * 전체상태 수정
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/all-progress")
    public TodoResponseDto.ALLUPDATE updateAllProgress(@RequestBody @Valid TodoRequestDto.ALLUPDATE updateDto) {
        return todoService.updateAllProgress(updateDto.getCurrentState());
    }
}
