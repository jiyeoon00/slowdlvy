package com.example.pilot.todo.presentation.dto;

import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.request.TodoUpdateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.application.dto.response.TodoDeleteResponseDto;
import com.example.pilot.todo.application.dto.response.TodoInfoResponseDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.presentation.dto.request.TodoCreateRequest;
import com.example.pilot.todo.presentation.dto.request.TodoUpdateRequest;
import com.example.pilot.todo.presentation.dto.response.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoAssembler {
    public static TodoCreateRequestDto todoCreateRequestDto(TodoCreateRequest todoCreateRequest) {
        return new TodoCreateRequestDto(todoCreateRequest.getText());
    }

    public static TodoCreateResponse todoCreateResponse(TodoCreateResponseDto todoCreateResponseDto) {
        return TodoCreateResponse.builder()
                .id(todoCreateResponseDto.getId())
                .text(todoCreateResponseDto.getText())
                .build();
    }

    public static TodoStatusChangeResponse todoStatusChangeResponse(TodoStatusChangeResponseDto todoStatusChangeResponseDto) {
        return new TodoStatusChangeResponse(todoStatusChangeResponseDto.getUpdateCount());
    }

    public static TodoUpdateRequestDto todoUpdateRequestDto(long todoId, TodoUpdateRequest todoUpdateRequest) {
        return TodoUpdateRequestDto.builder()
                .todoId(todoId)
                .text(todoUpdateRequest.getText())
                .build();
    }

    public static TodoDeleteResponse todoDeleteResponse(TodoDeleteResponseDto todoDeleteResponseDto) {
        return new TodoDeleteResponse(todoDeleteResponseDto.getDeleteCount());
    }

    public static TodoInfoResponse todoInfoResponse(TodoInfoResponseDto todoInfoResponseDto) {
        return TodoInfoResponse.builder()
                .id(todoInfoResponseDto.getId())
                .text(todoInfoResponseDto.getText())
                .status(todoInfoResponseDto.getStatus())
                .createdDate(todoInfoResponseDto.getCreatedDate())
                .build();
    }

    public static TodoInfoListResponse todoInfoListResponse(List<TodoInfoResponseDto> todoInfoResponseDtoList) {
        List<TodoInfoResponse> todoInfoResponseList = todoInfoResponseDtoList.stream()
                .map(TodoAssembler::todoInfoResponse)
                .collect(Collectors.toList());
        return new TodoInfoListResponse(todoInfoResponseList, todoInfoResponseList.size());
    }
}
