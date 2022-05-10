package com.example.pilottodolist.repository;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;

import java.util.List;

public interface TodoRepositoryCustom {

    Long deleteTodoListWithInQuery(List<Long> ids);
    List<Todo> getTodoList(Progress progress);
}
