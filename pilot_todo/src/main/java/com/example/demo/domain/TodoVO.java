package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Todo")
public class TodoVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String content;
    @Column(columnDefinition = "boolean default false")
    private boolean checked;

    @Builder
    public TodoVO(LocalDateTime createdAt, String content, boolean checked){
        this.createdAt = createdAt;
        this.content = content;
        this.checked = checked;
    }
}
