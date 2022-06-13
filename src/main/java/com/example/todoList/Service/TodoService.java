package com.example.todoList.Service;

import com.example.todoList.domain.Todo;
import com.example.todoList.domain.TodoRepository;
import com.example.todoList.domain.WorkStates;
import com.example.todoList.exception.CustomException;
import com.example.todoList.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    /**
     * 할 일 추가
     */
    public Long add(Todo todo){
        todoRepository.save(todo);
        return todo.getId();
    }


//    /**
//     *전체 조회
//     */
//    @Transactional(readOnly = true)
//    public List<Todo> searchAll(){
//        return todoRepository.findAll();
//    }
//
//
//    /**
//     *미 완료건 조회
//     */
//    @Transactional(readOnly = true)
//    public List<Todo> searchActive(){
//        return todoRepository.findByStates(WorkStates.ACTIVE);
//    }
//
//    /**
//     *완료건 조회
//     */
//    @Transactional(readOnly = true)
//    public List<Todo> searchCompleted(){ return todoRepository.findByStates(WorkStates.COMPLETED); }

    @Transactional(readOnly = true)
    public List<Todo> FindTodoByStatus(String status){
        if(status == null){
            return todoRepository.findAll();
        }else
            return todoRepository.findByStates(WorkStates.makeWordStates(status));
    }

    /**
     *단건 제거
     */
    public void deleteById(Long id){
        todoRepository.deleteById(id);
    }

    /**
     *완료건 제거
     */
    public void deleteCompleted(){
        todoRepository.deleteByStates(WorkStates.COMPLETED);
    }

    /**
     * todoList 수정
     */
    //public void Update(Long id, String workTitle){todoRepository.updateTodo(id, workTitle);}
    public void Update(Long id, String workTitle){
        try {
            Todo todo = todoRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TODO_NOT_FOUND));
            todo.setWorkTitle(workTitle);
        }catch (ObjectOptimisticLockingFailureException e){
            throw new ObjectOptimisticLockingFailureException(ErrorCode.OPTIMISTICLOCK.getDetail(), e.getCause());
        }
    }

    /**
     *단건 완료/단건 활성화
     */
    //public void changeState(Long id){todoRepository.changeState(id); }
    public void changeState(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.TODO_NOT_FOUND));
        todo.change();

    }



    /**
     * 전체 활성화
     */
    public void ActiveAllState(){
        todoRepository.changeAllState(WorkStates.ACTIVE, WorkStates.COMPLETED);
    }

    /**
     * 전체 완료
     */
    public void CompletedAllState(){
        todoRepository.changeAllState(WorkStates.COMPLETED, WorkStates.ACTIVE);
    }

}
