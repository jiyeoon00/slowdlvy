package com.example.pilot.todo.infrastructure;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.pilot.common.exception.TodoNotFoundException;
import com.example.pilot.todo.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class TodoQueryResolver implements GraphQLQueryResolver {
    private final TodoRepositoryImpl todoRepository;

    public Todo findTodoById(final long id) {
        return todoRepository.findById(id).orElseThrow(TodoNotFoundException::new);
    }
}
