package com.example.demo.repository;

import com.example.demo.domain.TodoVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoVO, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Todo t where t.id = :id")
    Optional<TodoVO> findById(Long id);
}
