package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;



    @GetMapping("/todo")
    public BaseResponse<List<TodoResDTO>> getTodoRes(){
        try{
            return new BaseResponse<>(todoService.getTodoRes());
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/todo")
    public BaseResponse<PostTodoResDTO> createTodo(@RequestBody TodoReqDTO todoReqDTO){
        try{
            return new BaseResponse<>(todoService.createTodo(todoReqDTO));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @DeleteMapping("/todo")
    public BaseResponse<Integer> updateTodo(@RequestParam int id, @RequestBody TodoReqDTO todoReqDTO){
        try{
            todoService.updateTodo(id, todoReqDTO);
            return new BaseResponse<>(Integer.valueOf(1));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PutMapping("/todo")
    public BaseResponse<Integer> deleteTodo(@RequestParam int id){
        try{
            todoService.deleteTodo(id);
            return new BaseResponse<>(Integer.valueOf(1));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
