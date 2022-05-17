package com.example.pilottodolist.controller;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.dto.TodoListInfo;
import com.example.pilottodolist.dto.TodoRequestDto;
import com.example.pilottodolist.dto.TodoResponseDto;
import com.example.pilottodolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 할일 추가
     */
    @PostMapping("/todo")
    public TodoResponseDto.CREATED createTodo(@RequestBody @Valid TodoRequestDto.CREATED created) {
        return todoService.create(created);
    }

    /**
     * 상태 수정
     */
    @PostMapping("/{todoId}/progress")
    public void changeProgress(@PathVariable("todoId") Long id) {
        todoService.changeProgress(id);
    }

    /**
     * 체크(COMPLETED)한 할일 삭제
     */
    @PostMapping(value = "/delete-todos")
    public void deleteTodoList(@RequestBody TodoRequestDto.DELETED deleted) {
        todoService.deleteTodoList(deleted);
    }

    /**
     * 전체 조회
     */
    @GetMapping("/todoList")
    public TodoListInfo<TodoResponseDto.GET> getTodoList(@RequestParam(value = "progress", required = false) Progress progress) {
        List<TodoResponseDto.GET> todoList = todoService.getTodoList(progress);
        return new TodoListInfo<>(todoList.size(), todoList);
    }

    /**
     * 전체상태 수정
     */
    @PostMapping("/all-progress")
    public TodoResponseDto.UPDATE updateAllProgress(@RequestBody TodoRequestDto.UPDATE isAllCheck) {
        return todoService.updateAllProgress(isAllCheck.getIsAllCheck());
    }
}
