package com.example.todoList.Controller;

import com.example.todoList.Dto.CreateTodoResponse;
import com.example.todoList.Dto.InfoTodoResponse;
import com.example.todoList.Dto.TodoRequest;
import com.example.todoList.Service.TodoService;
import com.example.todoList.domain.Todo;
import com.example.todoList.domain.WorkStates;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TodoApiController {

    private final TodoService todoService;

    //할 일 추가
    @PostMapping("/todo")
    public CreateTodoResponse createTodo(@RequestBody @Valid TodoRequest request){
        Long id = todoService.add(request);
        return new CreateTodoResponse(id);
    }

//    //전체 목록 조회
//    @GetMapping("/todo")
//    public List<Todo> SearchAll(){
//        return todoService.searchAll();
//    }
//
//    //목록 중 미 완료건 조회
//    @GetMapping("/todo/active")
//    public List<Todo> SearchActive(){
//        return todoService.searchActive();
//    }
//
//    //목록 중 완료건 조회
//    @GetMapping("/todo/completed")
//    public List<Todo> SearchCompleted(){
//        return todoService.searchCompleted();
//    }

    //목록 조회 {전체, 미완료건, 완료건}
    @GetMapping("/todo")
    public List<InfoTodoResponse> findTodoList(@RequestParam(value = "status", required = false) String status){
        return todoService.findTodoByStatus(status).stream()
                .map(InfoTodoResponse::new)
                .collect(Collectors.toList());
    }

    //단건 삭제
    @DeleteMapping("/todo/{id}")
    public void deleteOne(@PathVariable("id") Long id){
        todoService.deleteById(id);
    }

    //완료건 전체 삭제
    @DeleteMapping("/todo")
    public void deleteCompleted(){
        todoService.deleteCompleted();
    }

    //수정
    @PutMapping("/todo/{id}")
    public void updateOne(@PathVariable("id") Long id, @RequestBody @Valid TodoRequest request){
        todoService.update(id, request);
    }

    //단건 완료/단건 활성화
    @PutMapping("/todo/state/{id}")
    public void changeState(@PathVariable("id") Long id) {
        todoService.changeState(id);
    }

    //전체 활성화
    @PutMapping("/todo/active")
    public void activeAll(){
        todoService.activeAllState();
    }

    //전체 완료
    @PutMapping("/todo/completed")
    public void completedAll(){
        todoService.completedAllState();
    }

}
