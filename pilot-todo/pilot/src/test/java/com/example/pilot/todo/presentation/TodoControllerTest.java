package com.example.pilot.todo.presentation;

import com.example.pilot.todo.application.TodoService;
import com.example.pilot.todo.application.dto.request.TodoCreateRequestDto;
import com.example.pilot.todo.application.dto.response.TodoCreateResponseDto;
import com.example.pilot.todo.presentation.dto.TodoAssembler;
import com.example.pilot.todo.presentation.dto.request.TodoCreateRequest;
import com.example.pilot.todo.presentation.dto.response.TodoCreateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
    void 새로운_일정_저장_테스트() throws Exception{
        TodoCreateRequest todoCreateRequest = new TodoCreateRequest("test text");
        TodoCreateResponseDto responseDto = TodoCreateResponseDto.builder().id(1L).text(todoCreateRequest.getText()).build();
        TodoCreateResponse todoCreateResponse = TodoAssembler.todoCreateResponse(responseDto);

        given(todoService.save(any(TodoCreateRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoCreateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(todoCreateResponse)));
    }

    @Test
    void 단건_완료_활성화_테스트() throws Exception{
        mockMvc.perform(patch("/todo/{id}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(todoService).changeStatus(any(Long.class));
    }
}