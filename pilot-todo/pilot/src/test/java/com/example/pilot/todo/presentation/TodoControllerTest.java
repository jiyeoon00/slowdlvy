package com.example.pilot.todo.presentation;

import com.example.pilot.todo.application.TodoService;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.request.TodoUpdateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.application.dto.response.TodoStatusChangeResponseDto;
import com.example.pilot.todo.domain.TodoStatus;
import com.example.pilot.todo.presentation.dto.TodoAssembler;
import com.example.pilot.todo.presentation.dto.request.TodoCreateRequest;
import com.example.pilot.todo.presentation.dto.request.TodoStatusChangeRequest;
import com.example.pilot.todo.presentation.dto.request.TodoUpdateRequest;
import com.example.pilot.todo.presentation.dto.response.TodoCreateResponse;
import com.example.pilot.todo.presentation.dto.response.TodoStatusChangeResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        verify(todoService).changeStatus(any(Long.class));
    }

    @Test
    void 전체_완료_활성화_테스트() throws Exception{
        TodoStatusChangeRequest request = new TodoStatusChangeRequest(TodoStatus.COMPLETE);
        TodoStatusChangeResponseDto todoStatusChangeResponseDto = new TodoStatusChangeResponseDto(1);
        TodoStatusChangeResponse response = TodoAssembler.todoStatusChangeResponse(todoStatusChangeResponseDto);

        given(todoService.changeAllTodoStatus(any(TodoStatus.class))).willReturn(todoStatusChangeResponseDto);

        mockMvc.perform(patch("/todo/status")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
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
}