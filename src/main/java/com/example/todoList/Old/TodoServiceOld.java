package com.example.todoList.Old;

import com.example.todoList.domain.Todo;
import lombok.RequiredArgsConstructor;

import java.util.List;

//@Service
//@Transactional
@RequiredArgsConstructor
public class TodoServiceOld {

    private final TodoRepositoryOld todoRepository;

    /**
     * 할 일 추가
     */
    public Long add(Todo todo){
        todoRepository.save(todo);
        return todo.getId();
    }


    /**
     *전체 조회
     */
    public List<Todo> searchAll(){
        return todoRepository.findAll();
    }


    /**
     *미 완료건 조회
     */
    public List<Todo> searchActive(){
        return todoRepository.findActive();
    }

    /**
     *완료건 조회
     */
    public List<Todo> searchCompleted(){
        return todoRepository.findCompleted();
    }

    /**
     *단건 제거
     */
    public void deleteById(Long id){
        todoRepository.deleteOne(id);
    }

    /**
     *완료건 제거
     */
    public void deleteCompleted(){
        List<Todo> completed = todoRepository.findCompleted();
        completed.stream()
                .forEach(t -> todoRepository.deleteOne(t.getId()));
    }

    /**
     * todoList 수정
     */
    public void Update(Long id, String workTitle){
        todoRepository.updateTodo(id, workTitle);
    }

    /**
     *단건 완료/단건 활성화
     */
    public void changeState(Long id){
        todoRepository.changeState(id);
    }

    /**
     * 전체 활성화
     */
    public void ActiveAllState(){
        List<Todo> completed = todoRepository.findCompleted();
        completed.stream()
                .forEach(t -> todoRepository.changeState(t.getId()));
    }

    /**
     * 전체 완료
     */
    public void CompletedAllState(){
        List<Todo> completed = todoRepository.findActive();
        completed.stream()
                .forEach(t -> todoRepository.changeState(t.getId()));
    }

}
