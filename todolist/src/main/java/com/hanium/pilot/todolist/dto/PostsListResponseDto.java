package com.hanium.pilot.todolist.dto;

import  com.fasterxml.jackson.annotation.JsonFormat;
import com.hanium.pilot.todolist.domain.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.modifiedDate=entity.getModifiedDate();
    }
}
