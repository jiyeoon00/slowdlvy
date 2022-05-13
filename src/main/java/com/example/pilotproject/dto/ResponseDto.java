package com.example.pilotproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
    private int statusCode;
    private String responseMessage;
    private T data;
}
