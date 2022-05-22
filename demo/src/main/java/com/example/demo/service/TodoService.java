package com.example.demo.service;

import com.example.demo.domain.BaseException;
import com.example.demo.domain.PostTodoResDTO;
import com.example.demo.domain.TodoReqDTO;
import com.example.demo.domain.TodoResDTO;
import com.example.demo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo.domain.BaseResponseStatus.DATABASE_ERROR;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    // 목록 불러오기
    public List<TodoResDTO> getTodoRes() throws BaseException {
        try{
            return todoRepository.getTodoRes();
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 생성
    public PostTodoResDTO createTodo(TodoReqDTO todoReqDTO) throws BaseException {
        try{
            return new PostTodoResDTO(todoRepository.creatTodo(todoReqDTO));
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 수정
    public void updateTodo(int id, TodoReqDTO todoReqDTO) throws BaseException{
        try{
            todoRepository.updateTodo(id, todoReqDTO);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 삭제
    public void deleteTodo(int id) throws BaseException{
        try{
            todoRepository.deleteTodo(id);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
