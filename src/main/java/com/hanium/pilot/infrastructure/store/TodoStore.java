package com.hanium.pilot.infrastructure.store;

import com.hanium.pilot.domain.model.State;
import com.hanium.pilot.domain.model.Todo;
import com.hanium.pilot.infrastructure.repository.TodoRepository;
import com.hanium.pilot.presentation.dto.UpdateAllTodoStateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.hanium.pilot.common.response.ResponseState.NOT_EXIST_TODO;

@Component
@RequiredArgsConstructor
public class TodoStore {
    private final TodoRepository todoRepository;

    public Todo saveTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public Todo getExistTodo(Long id) {
        return todoRepository.findExistTodoById(id,State.DELETED)
                .orElseThrow(()->new RuntimeException(NOT_EXIST_TODO));
    }

    public Todo updateTodoState(Todo todo, String state) {
        todo.setState(State.valueOf(state));
        return todo;
    }

    public Todo updateTodoTitle(Todo todo, String title) {
        todo.setTitle(title);
        return todo;
    }

    public List<Todo> getExistAllTodo() {
        return todoRepository.findExistAllTodo(State.DELETED);
    }

    public List<Todo> updateAllTodoState(String state, List<Todo> todoList) {
        return todoList.stream()
                .map(t->updateTodoState(t,state))
                .collect(Collectors.toList());
    }


}
