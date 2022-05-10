package com.example.pilottodolist.service;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import com.example.pilottodolist.dto.TodoListInfo;
import com.example.pilottodolist.dto.TodoRequestDto;
import com.example.pilottodolist.dto.TodoResponseDto;
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

        Todo todo = repository.save(Todo.builder()
                                        .content(created.getContent())
                                        .progress(Progress.ACTIVE)
                                        .build());

        return new TodoResponseDto.CREATED(todo);
    }

    /**
     *  Active <-> Completed 상태 변화
     */
    @Transactional
    public void changeProgress(Long id) {

        /**
         * 동시성 고려
         * 수정 중 누가 지울수도 있고, 수정할 수도 있음
         */
        repository.findById(id)
                .orElseThrow(() -> new OptimisticLockingFailureException("동시 접근 중 입니다.")).changeProgress();
    }

    /**
     * 체크한 Todo에 한해 삭제한다.
     */
    @Transactional
    public Long deleteTodoList(TodoRequestDto.DELETED deleted) {

        /**
         * 동시성 고려
         * 삭제 중 누가 수정 할 수 있고, 상태를 바꿀 수도 있다.
         * 클릭 (find) 할 때 부터 락을 걸어야 할 것 같다.
         */
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
     * 전체 TodoList의 상태 ACTIVE <-> COMPLETED 변경
     */
    @Transactional
    public TodoResponseDto.UPDATE updateAllProgress(Boolean isAllCheck) {

        /**
         * 동시성 검사 필요
         */

        if(isAllCheck) repository.bulkAllProgressToActive(Progress.ACTIVE);
        else repository.bulkAllProgressToCompleted(Progress.COMPLETED);

        return new TodoResponseDto.UPDATE(!isAllCheck);
    }

}
