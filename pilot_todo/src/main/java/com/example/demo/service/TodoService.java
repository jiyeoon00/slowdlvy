package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.repository.JdbcTemplateTodoRepository;
import com.example.demo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.domain.BaseResponseStatus.DATABASE_ERROR;

@Service
@AllArgsConstructor
public class TodoService {


    private final JdbcTemplateTodoRepository jdbcTemplateTodoRepository;

    // 목록 불러오기
    public List<TodoResDTO> getTodoRes() throws BaseException {
        try{
            return jdbcTemplateTodoRepository.getTodoRes();
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 생성
    public PostTodoResDTO createTodo(TodoReqDTO todoReqDTO) throws BaseException {
        try{
            return new PostTodoResDTO(jdbcTemplateTodoRepository.creatTodo(todoReqDTO));
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 수정
    public void updateTodo(int id, TodoReqDTO todoReqDTO) throws BaseException{
        try{
            jdbcTemplateTodoRepository.updateTodo(id, todoReqDTO);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 삭제
    public void deleteTodo(int id) throws BaseException{
        try{
            jdbcTemplateTodoRepository.deleteTodo(id);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
