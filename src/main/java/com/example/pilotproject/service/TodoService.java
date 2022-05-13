package com.example.pilotproject.service;

import com.example.pilotproject.dto.CreateDto;
import com.example.pilotproject.entity.Todo;
import com.example.pilotproject.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TodoService {

    @Autowired
    TodoRepository todoRepository;

    // create -> 단건추가
    public void register(CreateDto createDto){
        Todo todo = Todo.builder()
                .content(createDto.getContent())
                .isComplete(false)
                .build();

        todoRepository.save(todo);
    }

    // read -> 단건조회 (상태에 따른)
    public List searchListByState(Boolean isCompleted){
        return todoRepository.findAllByIsComplete(isCompleted);
    }

    // read -> 전체목록조회
    public List searchAll(){
        return todoRepository.findAll();
    }

    // update -> 내용 수정
    public Optional<Todo> updateContent(Long id, String updateContent){
        Optional<Todo> todo = todoRepository.findById(id);

        if(todo.isPresent() && todo.get().getIsComplete() == false){
            todo.get().update(updateContent);
            todoRepository.save(todo.get());
            return todo;
        }
        else return null;
    }

    // update -> 단건완료/단건활성화
    public Optional<Todo> updateState(Long id){
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()) {
            todo.get().update(todo.get().getIsComplete());
            todoRepository.save(todo.get());
            return todo;
        }

        else return null;
    }

    // update -> 전체완료/전체활성화
    public List<Todo> updateAllState(){
        List<Todo> todoList = todoRepository.findAll();

        boolean isExist = todoList.stream().allMatch(todo -> todo.getIsComplete().equals(true));
        boolean isExist2 = todoList.stream().anyMatch(todo -> todo.getIsComplete().equals(true));

        // if 목록 전체가 다 완료 then 전체 활성화
        if(isExist) todoList.forEach(todo -> {
            todo.update(todo.getIsComplete());
            todoRepository.save(todo);
        });

        // if 목록 전체가 다 활성화 then 전체 완료
        if(!isExist) todoList.forEach(todo -> {
            todo.update(todo.getIsComplete());
            todoRepository.save(todo);
        });

        // if 하나라도 완료 된게 존재 then 나머지 활성화 목록을 완료로 상태변화
        if(isExist2) todoList.forEach(todo -> {
            todo.update(false);
            todoRepository.save(todo);
        });


        return todoRepository.findAll();
    }


    // delete -> 단건 제거
    public void delete(Long id){
        todoRepository.deleteById(id);
    }

    // delete -> 완료건 제거
    public void deleteAll(){
        List<Todo> todoList = todoRepository.findAllByIsComplete(true);
        todoList.forEach(todoRepository::delete);

    }

}
