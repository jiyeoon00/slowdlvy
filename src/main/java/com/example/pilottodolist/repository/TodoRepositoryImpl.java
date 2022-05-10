package com.example.pilottodolist.repository;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;
import static com.example.pilottodolist.domain.QTodo.todo;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Long deleteTodoListWithInQuery(List<Long> ids) {
        return queryFactory.delete(todo)
                .where(todo.Id.in(ids))
                .execute();
    }

    @Override
    public List<Todo> getTodoList(Progress progress) {
        return queryFactory.select(todo)
                .from(todo)
                .where(progressEq(progress))
                .fetch();
    }

    private BooleanExpression progressEq(Progress progress) {
        if(progress == Progress.ACTIVE) {
            return todo.progress.eq(Progress.ACTIVE);
        } else if(progress == Progress.COMPLETED) {
            return todo.progress.eq(Progress.COMPLETED);
        } else { // QueryParam is null
            return null;
        }
    }


}
