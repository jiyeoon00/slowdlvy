package com.hanium.pilot.presentation.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.hanium.pilot.application.service.TodoService;
import com.hanium.pilot.presentation.dto.GetTodoRequest;
import com.hanium.pilot.presentation.dto.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoQuery implements GraphQLQueryResolver {

    private final TodoService todoService;

    TodoResponse getTodo(GetTodoRequest request){
        return todoService.getTodo(request);
    }

    List<TodoResponse> getAllTodo(){
        return todoService.getAllTodo();
    }
}
