package com.hanium.pilot.presentation.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.hanium.pilot.application.service.TodoService;
import com.hanium.pilot.common.response.Response;
import com.hanium.pilot.domain.model.State;
import com.hanium.pilot.presentation.dto.CreateTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.hanium.pilot.common.response.ResponseState.SUCCESS;

@Component
@RequiredArgsConstructor
public class TodoMutation implements GraphQLMutationResolver {

    private final TodoService todoService;

    public CreateTodo.Response createTodo(CreateTodo.Request request){
        return todoService.createTodo(request);
    }



}
