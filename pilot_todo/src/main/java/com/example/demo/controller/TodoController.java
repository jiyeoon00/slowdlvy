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

    /**
     * JPA ver.
     */

    @GetMapping("/todo")
    public BaseResponse<List<TodoVO>> getTodoRes(){
        try{
            return new BaseResponse<>(todoService.findAll());
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/todo")
    public BaseResponse<TodoVO> createTodo(@RequestBody TodoVO todoVO){
        try{
            return new BaseResponse<>(todoService.save(todoVO));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PutMapping("/todo/{id}")
    public BaseResponse<String> updateTodo(@PathVariable Long id, @RequestBody TodoVO todoVO){
        try{
            todoService.updateById(id, todoVO);
            return new BaseResponse<>(String.valueOf("update success"));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @DeleteMapping("/todo/{id}")
    public BaseResponse<String> deleteTodo(@PathVariable Long id){
        try{
            todoService.deleteById(id);
            return new BaseResponse<>(String.valueOf("delete success"));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PutMapping("/todo/state/{id}")
    public BaseResponse<String> activateTodo(@PathVariable Long id){
        try{
            todoService.activateById(id);
            return new BaseResponse<>(String.valueOf("activation change success"));
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * JdbcTemplate ver.
     *

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
    */
}
