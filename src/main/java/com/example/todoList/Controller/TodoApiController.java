package com.example.todoList.Controller;

import com.example.todoList.Dto.CreateTodoResponse;
import com.example.todoList.Dto.TodoRequest;
import com.example.todoList.Service.TodoService;
import com.example.todoList.domain.Todo;
import com.example.todoList.domain.WorkStates;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoApiController {

    private final TodoService todoService;

    //할 일 추가
    @PostMapping("/todo")
    public CreateTodoResponse CreateTodo(@RequestBody @Valid TodoRequest request){
        Todo todo = new Todo(request.getWorkTitle(), WorkStates.ACTIVE);
        Long id = todoService.add(todo);
        return new CreateTodoResponse(id);
    }

    //전체 목록 조회
    @GetMapping("/todo")
    public List<Todo> SearchAll(){
        return todoService.searchAll();
    }

    //목록 중 미 완료건 조회
    @GetMapping("/todo/active")
    public List<Todo> SearchActive(){
        return todoService.searchActive();
    }

    //목록 중 완료건 조회
    @GetMapping("/todo/completed")
    public List<Todo> SearchCompleted(){
        return todoService.searchCompleted();
    }

    //단건 삭제
    @DeleteMapping("/todo/{id}")
    public void DeleteOne(@PathVariable("id") Long id){
        todoService.deleteById(id);
    }

    //완료건 전체 삭제
    @DeleteMapping("/todo")
    public void DeleteOne(){
        todoService.deleteCompleted();
    }

    //수정
    @PutMapping("/todo/{id}")
    public void Update(@PathVariable("id") Long id, @RequestBody @Valid TodoRequest request){
        todoService.Update(id, request.getWorkTitle());
    }

    //단건 완료/단건 활성화
    @PutMapping("/todo/state/{id}")
    public void ChangeState(@PathVariable("id") Long id){
        todoService.changeState(id);
    }

    //전체 활성화
    @PutMapping("/todo/active")
    public void ActiveAll(){
        todoService.ActiveAllState();
    }

    //전체 완료
    @PutMapping("/todo/completed")
    public void ComplitedAll(){
        todoService.CompletedAllState();
    }

}
