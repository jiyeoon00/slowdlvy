package com.example.pilot.todo.presentation;

import com.example.pilot.todo.application.TodoService;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoDeleteResponseDto;
import com.example.pilot.todo.application.dto.response.TodoInfoResponseDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.presentation.dto.TodoAssembler;
import com.example.pilot.todo.presentation.dto.request.TodoCreateRequest;
import com.example.pilot.todo.presentation.dto.request.TodoStatusChangeRequest;
import com.example.pilot.todo.presentation.dto.request.TodoUpdateRequest;
import com.example.pilot.todo.presentation.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/todo")
@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    // 단건 추가
    @PostMapping
    public TodoCreateResponse todoSave(@RequestBody @Valid TodoCreateRequest todoCreateRequest) {
        TodoCreateRequestDto todoCreateRequestDto = TodoAssembler.todoCreateRequestDto(todoCreateRequest);
        return TodoAssembler.todoCreateResponse(todoService.save(todoCreateRequestDto));
    }

    // 내용 수정
    @PatchMapping("/{id}")
    public void todoUpdate(@PathVariable("id") long todoId, @RequestBody @Valid TodoUpdateRequest todoUpdateRequest) {
        todoService.update(TodoAssembler.todoUpdateRequestDto(todoId, todoUpdateRequest));
    }

    // 단건 완료<->활성화
    @PatchMapping("/{id}/status")
    public void todoStatusToggle(@PathVariable("id") long todoId) {
        todoService.toggleStatus(todoId);
    }

    // 전체 완료/활성화
    @PatchMapping("/status")
    public TodoStatusChangeResponse allTodoStatusChange(@RequestBody TodoStatusChangeRequest todoStatusChangeRequest) {
        TodoStatusChangeResponseDto todoStatusChangeResponseDto = todoService.changeAllTodoStatus(todoStatusChangeRequest.getStatus());
        return TodoAssembler.todoStatusChangeResponse(todoStatusChangeResponseDto);
    }

    // 단건 삭제
    @DeleteMapping("/{id}")
    public void todoDelete(@PathVariable("id") long todoId) {
        todoService.delete(todoId);
    }

    // 완료건 삭제
    @DeleteMapping("/complete")
    public TodoDeleteResponse completeTodoDelete() {
        TodoDeleteResponseDto todoDeleteResponseDto = todoService.deleteAllCompleteTodo();
        return TodoAssembler.todoDeleteResponse(todoDeleteResponseDto);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public TodoInfoResponse todoFind(@PathVariable("id") long todoId) {
        TodoInfoResponseDto todoInfoResponseDto = todoService.find(todoId);
        return TodoAssembler.todoInfoResponse(todoInfoResponseDto);
    }

    /* 목록 조회
       status = null : 전체 목록 조회
       status = active : 활성 목록 조회
       status = complete : 완료 목록 조회
       그 외 status : InvalidTodoStatusException 발생
     */
    @GetMapping
    public TodoInfoListResponse todoListFind(@RequestParam(value = "status", required = false) String status)  {
        List<TodoInfoResponseDto> todoInfoResponseDtoList = todoService.findTodoList(status);
        return TodoAssembler.todoInfoListResponse(todoInfoResponseDtoList);
    }
}
