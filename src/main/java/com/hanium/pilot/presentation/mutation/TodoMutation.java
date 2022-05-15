package com.hanium.pilot.presentation.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.hanium.pilot.application.service.TodoService;

import com.hanium.pilot.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;


@Component
@RequiredArgsConstructor
@Validated
public class TodoMutation implements GraphQLMutationResolver {

    private final TodoService todoService;

    public TodoResponse createTodo(CreateTodoRequest request){
        return todoService.createTodo(request);
    }

    public TodoResponse updateTodoState(@Valid UpdateTodoStateRequest request){
        return todoService.updateTodoState(request);
    }

    public TodoResponse updateTodoTitle(UpdateTodoTitleRequest request){
        return todoService.updateTodoTitle(request);
    }

    public List<TodoResponse> updateAllTodoState(UpdateAllTodoStateRequest request){
        return todoService.updateAllTodoState(request);
    }


}
