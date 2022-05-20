package com.example.pilottodolist.service;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import com.example.pilottodolist.dto.DtoFormatter;
import com.example.pilottodolist.dto.TodoListInfo;
import com.example.pilottodolist.dto.TodoRequestDto;
import com.example.pilottodolist.dto.TodoResponseDto;
import com.example.pilottodolist.exception.NotFoundException;
import com.example.pilottodolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TodoService {

    private final TodoRepository repository;

    /**
     *  TodoList 생성
     */
    @Transactional
    public TodoResponseDto.CREATED create(TodoRequestDto.CREATED created) {

        Todo todo = repository.save(DtoFormatter.CreateRequestToTodo(created));
        return new TodoResponseDto.CREATED(todo);
    }

    /**
     *  Concurrent
     *  Active <-> Completed 상태 변화
     */
    @Transactional
    public void changeProgress(Long id) {

        repository.findById(id).orElseThrow(NotFoundException::new).changeProgress();
    }

    /**
     * Concurrent
     * 체크한 Todo에 한해 삭제한다.
     */
    @Transactional
    public Long deleteTodoList(TodoRequestDto.DELETED deleted) {

        return repository.deleteTodoListWithInQuery(deleted.getIds());
    }

    /**
     * 전체 조회 , 상태별 조회로 나누는 것이 적합하나
     * 이번 프로젝트는 필터링 요소가 상태 외에 없으므로 그냥 한 메소드 안에서 해결되도록 한다.
     */
    public List<TodoResponseDto.GET> getTodoList(Progress progress) {

        return repository.getTodoList(progress)
                .stream().map(t -> new TodoResponseDto.GET(t)).collect(Collectors.toList());
    }

    /**
     * Concurrent
     * 전체 TodoList의 상태 ACTIVE <-> COMPLETED 변경
     */
    @Transactional
    public TodoResponseDto.ALLUPDATE updateAllProgress(String currentState) {

        repository.bulkAllProgress(Progress.of(currentState));
        return new TodoResponseDto.ALLUPDATE(currentState);
    }
}
