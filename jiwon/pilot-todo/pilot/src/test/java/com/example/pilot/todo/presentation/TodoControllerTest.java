package com.example.pilot.todo.presentation;

import com.example.pilot.todo.application.TodoService;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.request.TodoUpdateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.application.dto.response.TodoDeleteResponseDto;
import com.example.pilot.todo.application.dto.response.TodoInfoResponseDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.domain.TodoStatus;
import com.example.pilot.todo.presentation.dto.TodoAssembler;
import com.example.pilot.todo.presentation.dto.request.TodoCreateRequest;
import com.example.pilot.todo.presentation.dto.request.TodoStatusChangeRequest;
import com.example.pilot.todo.presentation.dto.request.TodoUpdateRequest;
import com.example.pilot.todo.presentation.dto.response.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TodoController.class)
class TodoControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TodoService todoService;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void 새로운_Todo_저장_테스트() throws Exception{
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("test text");
        TodoCreateResponseDto responseDto = TodoCreateResponseDto.builder().id(1L).text(todoCreateRequest.getText()).build();
        TodoCreateResponse todoCreateResponse = TodoAssembler.todoCreateResponse(responseDto);

        given(todoService.save(any(TodoCreateRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(post("/todo")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoCreateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(todoCreateResponse)));
    }

    @Test
    void 단건_완료_활성화_테스트() throws Exception{
        mockMvc.perform(patch("/todo/{id}/status", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(todoService).toggleStatus(any(Long.class));
    }

    @Test
    void 전체_완료_활성화_테스트() throws Exception{
        TodoStatusChangeRequest request = new TodoStatusChangeRequest(TodoStatus.COMPLETE);
        TodoStatusChangeResponseDto todoStatusChangeResponseDto = new TodoStatusChangeResponseDto(1);
        TodoStatusChangeResponse response = TodoAssembler.todoStatusChangeResponse(todoStatusChangeResponseDto);

        given(todoService.changeAllTodoStatus(any(TodoStatus.class))).willReturn(todoStatusChangeResponseDto);

        mockMvc.perform(patch("/todo/status")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void Todo_내용_변경_테스트() throws Exception{
        TodoUpdateRequest todoUpdateRequest = new TodoUpdateRequest("new text");

        mockMvc.perform(patch("/todo/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoUpdateRequest)))
                .andExpect(status().isOk());

        verify(todoService).update(any(TodoUpdateRequestDto.class));
    }

    @Test
    void 단건_Todo_제거_테스트() throws Exception{
        mockMvc.perform(delete("/todo/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(todoService).delete(any(Long.class));
    }

    @Test
    void 완료건_제거_테스트() throws Exception{
        TodoDeleteResponseDto todoDeleteResponseDto = new TodoDeleteResponseDto(3);
        TodoDeleteResponse response = TodoAssembler.todoDeleteResponse(todoDeleteResponseDto);
        given(todoService.deleteAllCompleteTodo()).willReturn(todoDeleteResponseDto);

        mockMvc.perform(delete("/todo/complete")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void 단건_Todo_조회_테스트() throws Exception{
        TodoInfoResponseDto todoInfoResponseDto = TodoInfoResponseDto.builder()
                .id(1L)
                .text("find text")
                .status(TodoStatus.ACTIVE)
                .createdDate(LocalDateTime.now())
                .build();
        TodoInfoResponse response = TodoAssembler.todoInfoResponse(todoInfoResponseDto);
        given(todoService.find(any(Long.class))).willReturn(todoInfoResponseDto);

        mockMvc.perform(get("/todo/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void 전체_Todo_목록_조회_테스트() throws Exception{
        List<TodoInfoResponseDto> todoInfoResponseDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TodoInfoResponseDto todoInfoResponseDto = TodoInfoResponseDto.builder().id(i)
                    .text("text" + i)
                    .createdDate(LocalDateTime.now())
                    .status(i % 2 == 0 ? TodoStatus.ACTIVE : TodoStatus.COMPLETE)
                    .build();
            todoInfoResponseDtoList.add(todoInfoResponseDto);
        }

        TodoInfoListResponse response = TodoAssembler.todoInfoListResponse(todoInfoResponseDtoList);
        given(todoService.findTodoList(null)).willReturn(todoInfoResponseDtoList);

        mockMvc.perform(get("/todo")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void 활성화_Todo_목록_조회_테스트() throws Exception{
        String status = "active";

        List<TodoInfoResponseDto> todoInfoResponseDtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TodoInfoResponseDto todoInfoResponseDto = TodoInfoResponseDto.builder().id(i)
                    .text("text" + i)
                    .createdDate(LocalDateTime.now())
                    .status(TodoStatus.ACTIVE)
                    .build();
            todoInfoResponseDtoList.add(todoInfoResponseDto);
        }

        TodoInfoListResponse response = TodoAssembler.todoInfoListResponse(todoInfoResponseDtoList);
        given(todoService.findTodoList(status)).willReturn(todoInfoResponseDtoList);

        mockMvc.perform(get("/todo?status=" + status)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void 완료_Todo_목록_조회_테스트() throws Exception{
        String status = "complete";

        List<TodoInfoResponseDto> todoInfoResponseDtoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TodoInfoResponseDto todoInfoResponseDto = TodoInfoResponseDto.builder().id(i)
                    .text("text" + i)
                    .createdDate(LocalDateTime.now())
                    .status(TodoStatus.COMPLETE)
                    .build();
            todoInfoResponseDtoList.add(todoInfoResponseDto);
        }

        TodoInfoListResponse response = TodoAssembler.todoInfoListResponse(todoInfoResponseDtoList);
        given(todoService.findTodoList(status)).willReturn(todoInfoResponseDtoList);

        mockMvc.perform(get("/todo?status=" + status)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}