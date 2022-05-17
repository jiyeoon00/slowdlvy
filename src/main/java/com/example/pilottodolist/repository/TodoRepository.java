package com.example.pilottodolist.repository;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {

    @Modifying(clearAutomatically = true)
    @Query("update Todo t set t.progress = :progress, t.version = t.version + 1 where t.progress <> :progress")
    void bulkAllProgress(Progress progress);
}
