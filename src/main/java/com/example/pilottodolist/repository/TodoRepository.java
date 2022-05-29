package com.example.pilottodolist.repository;

import com.example.pilottodolist.domain.Progress;
import com.example.pilottodolist.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {

    @Modifying(clearAutomatically = true)
    @Query("update Todo t set t.progress = :progress, t.version = t.version + 1")
    void bulkAllProgress(Progress progress);
}
