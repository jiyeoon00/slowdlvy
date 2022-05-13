package com.example.pilotproject.controller;

import com.example.pilotproject.dto.CreateDto;
import com.example.pilotproject.dto.ResponseDto;
import com.example.pilotproject.dto.UpdateDto;
import com.example.pilotproject.entity.Todo;
import com.example.pilotproject.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private final TodoService todoService;

    // 등록
    @PostMapping("")
    public ResponseEntity register(@RequestBody CreateDto createDto){
        todoService.register(createDto);

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(200)
                .responseMessage("등록 성공")
                .data(createDto)
                .build(), HttpStatus.OK);
    }

    // 전체 목록 조회
    @GetMapping("")
    public ResponseEntity getAllList(){
        List list = todoService.searchAll();

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(200)
                .responseMessage("전체 목록 조회")
                .data(list)
                .build(), HttpStatus.OK);

    }

    // 상태에 따른 목록 조회
    @GetMapping(value = "/state")
    public ResponseEntity getActiveList(@RequestParam Boolean isComplete){
        List list = todoService.searchListByState(isComplete);

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(200)
                .responseMessage("상태(=" + isComplete +") 목록 조회")
                .data(list)
                .build(), HttpStatus.OK);
    }

    // 내용 수정
    @PatchMapping("/{id}/content")
    public ResponseEntity updateContent(@PathVariable Long id, @RequestBody UpdateDto updateDto){
        Optional<Todo> todo = todoService.updateContent(id, updateDto.getContent());

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(200)
                .responseMessage("내용 변경 완료")
                .data(todo.get())
                .build(), HttpStatus.OK);
    }

    // 단건 상태 수정
    @PatchMapping("/{id}/state")
    public ResponseEntity updateState(@PathVariable Long id){
        Optional<Todo> todo = todoService.updateState(id);

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(200)
                .responseMessage("단건 상태 변경 완료")
                .data(todo)
                .build(), HttpStatus.OK);
    }

    // 전체 상태 수정
    @PutMapping("")
    public ResponseEntity updateAllState(){
        List<Todo> todoList = todoService.updateAllState();

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(200)
                .responseMessage("전체 목록 상태 변경")
                .data(todoList)
                .build(), HttpStatus.OK);
    }

    // 단건 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        todoService.delete(id);

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(200)
                .responseMessage("단건 삭제")
                .data("")
                .build(), HttpStatus.OK);
    }

    // 완료 항목 전체 삭제
    @DeleteMapping("")
    public ResponseEntity deleteAll(){
        todoService.deleteAll();

        return new ResponseEntity(ResponseDto.builder()
                .statusCode(200)
                .responseMessage("완료 항목 전체 삭제")
                .data("")
                .build(), HttpStatus.OK);
    }
}
