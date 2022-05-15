package com.example.todoList.Old;

import com.example.todoList.domain.Todo;
import com.example.todoList.domain.WorkStates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryOld {

    private final EntityManager em;

    public void save(Todo todo){
        em.persist(todo);
    }

    public Todo findOne(Long id){
        return em.find(Todo.class, id);
    }

    //전체목록 조회
    //public List<Todo> findAll(){return em.createQuery("select t from Todo t", Todo.class).getResultList();}

    //전체목록 조회
    public List<Todo> findAll(){
        return em.createQuery("select t from Todo t", Todo.class).getResultList();}

    //미완료건 조회
    public List<Todo> findActive(){
        return em.createQuery("select t from Todo t where t.states = :states", Todo.class)
                .setParameter("states", WorkStates.ACTIVE)
                .getResultList();
    }

    //완료건 조회
    public List<Todo> findCompleted(){
        return em.createQuery("select t from Todo t where t.states = :states", Todo.class)
                .setParameter("states", WorkStates.COMPLETED)
                .getResultList();
    }

    //수정
    public Long updateTodo(Long id, String workTitle){
        Todo todo = em.find(Todo.class, id);
        todo.setWorkTitle(workTitle);
        return id;
    }

    //단건 제거
    public void deleteOne(Long id){
        Todo findTodo = em.find(Todo.class, id);
        em.remove(findTodo);
    }

    //단건완료/단건 활성화
    public void changeState(Long id){
        Todo todo = em.find(Todo.class, id);
        todo.change();
    }

}
