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
    /**
     *  JPA ver.
     */

    private final TodoRepository todoRepository;

    // 목록 불러오기
    public List<TodoVO> findAll() throws BaseException {
        try{
            List<TodoVO> todos = new ArrayList<>();
            todoRepository.findAll().forEach(e -> todos.add(e));
            return todos;
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 수정
    @Transactional
    public void updateById(Long id, TodoVO todo) throws BaseException {
        try{
            Optional<TodoVO> e = todoRepository.findById(id);

            if(e.isPresent()) {
                e.get().setContent(todo.getContent());
                e.get().setCreatedAt(todo.getCreatedAt());
                e.get().setChecked(todo.isChecked());
                todoRepository.save(e.get());
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 생성
    @Transactional
    public TodoVO save(TodoVO todo) throws BaseException {
        try{
            todoRepository.save(todo);
            return todo;
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 삭제
    @Transactional
    public void deleteById(Long id) throws BaseException {
        try{
            todoRepository.deleteById(id);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 활성화 변경
    @Transactional
    public void activateById(Long id) throws BaseException {
        try{
            Optional<TodoVO> e = todoRepository.findById(id);

            if(e.isPresent()) {
                if (!e.get().isChecked()) e.get().setChecked(true);
                else e.get().setChecked(false);
                todoRepository.save(e.get());
            }
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    /**
     * JdbcTemplate ver.
     *

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
    */
}
