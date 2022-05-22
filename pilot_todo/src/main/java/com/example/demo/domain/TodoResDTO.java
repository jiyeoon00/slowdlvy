package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TodoResDTO {
    private int id;
    private LocalDateTime createdAt;
    private String content;
}
