package com.hanium.pilot.todolist.dto;

import com.hanium.pilot.todolist.domain.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsSaveRequestDto(String title,String content){
        this.title=title;
        this.content=content;
    }
    public Posts toEntity() {
        return Posts.builder().title(title).content(content).build();
    }
}
