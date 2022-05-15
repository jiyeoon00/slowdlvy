package com.example.pilot.todo.infrastructure;

import com.example.pilot.todo.domain.Todo;
import com.example.pilot.todo.domain.TodoStatus;
import com.example.pilot.todo.domain.repository.TodoRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

import static com.example.pilot.todo.domain.QTodo.todo;

@RequiredArgsConstructor
@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final TodoDao todoDao;
    private final JPAQueryFactory queryFactory;

    @Override
    public Todo save(Todo todo) {
        return todoDao.save(todo);
    }

    @Override
    public Optional<Todo> findById(long todoId) {
        return todoDao.findById(todoId);
    }

    @Override
    public long updateAllStatus(TodoStatus status) {
        return queryFactory.update(todo)
                .set(todo.status, status)
                .execute();
    }

    @Override
    public void delete(long todoId) {
        todoDao.deleteById(todoId);
    }

    @Override
    public long deleteAllComplete() {
        return queryFactory.delete(todo)
                .where(todo.status.eq(TodoStatus.COMPLETE))
                .execute();
    }

    @Override
    public List<Todo> findAllByStatus(TodoStatus status) {
        return queryFactory.selectFrom(todo)
                .where(getStatusEq(status))
                .fetch();
    }

    @Override
    public Optional<Todo> findByIdForUpdate(long todoId) {
        return Optional.ofNullable(queryFactory.selectFrom(todo)
                .where(todo.id.eq(todoId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne());
    }

    private BooleanExpression getStatusEq(TodoStatus status) {
        return status != null ? todo.status.eq(status) : null;
    }
}
