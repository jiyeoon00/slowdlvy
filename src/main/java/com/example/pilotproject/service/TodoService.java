package com.example.pilotproject.service;

import com.example.pilotproject.dto.CreateDto;
import com.example.pilotproject.dto.UpdateDto;
import com.example.pilotproject.entity.State;
import com.example.pilotproject.entity.Todo;
import com.example.pilotproject.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    @Autowired
    private final TodoRepository todoRepository;

    // create -> 단건추가
    public Todo register(CreateDto createDto){
        Todo todo = createDto.toEntity();
        return todoRepository.save(todo);
    }

    // read -> 단건조회 (상태에 따른)
    @Transactional(readOnly = true)
    public List searchListByState(String state){
        State requestState = State.valueOf(state.toUpperCase());
        return todoRepository.findALlByState(requestState);
    }

    // read -> 전체목록조회
    @Transactional(readOnly = true)
    public List searchAll(){
        return todoRepository.findAll();
    }

    // update -> 내용 수정
    public Optional<Todo> updateContent(Long id, UpdateDto updateDto){
        Optional<Todo> todo = todoRepository.findById(id);

        if(todo.isPresent() && todo.get().getState().equals(State.ACTIVE)){
            todo.get().update(updateDto.getContent());
            todoRepository.save(todo.get());
        }
        return todo;
    }

    // update -> 단건완료/단건활성화
    public Optional<Todo> updateState(Long id){
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()) {
            todo.get().update();
            todoRepository.save(todo.get());
        }
        return todo;
    }

    // update -> 전체완료/전체활성화
    public List<Todo> updateAllState(){
        List<Todo> todoList = todoRepository.findAll();


        todoList.forEach(todo -> {
            todo.update();
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
        List<Todo> todoList = todoRepository.findALlByState(State.COMPLETE);
        todoList.forEach(todoRepository::delete);

    }

}
