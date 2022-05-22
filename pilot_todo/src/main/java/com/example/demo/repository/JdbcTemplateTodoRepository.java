package com.example.demo.repository;

import com.example.demo.domain.TodoReqDTO;
import com.example.demo.domain.TodoResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcTemplateTodoRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 등록
    public int creatTodo(TodoReqDTO todoReqDTO){
        String createTodoQuery = "insert into Todo (createdAt, content) values (?, ?)";

        this.jdbcTemplate.update(createTodoQuery, todoReqDTO.getCreatedAt(), todoReqDTO.getContent());
        String todoIdx = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(todoIdx, int.class);
    }

    // list 받아오기
    public List<TodoResDTO> getTodoRes(){
        String getTodoResQuery = "select id, createdAt, content from Todo";
        return this.jdbcTemplate.query(getTodoResQuery,
                (rs, rowNum) -> new TodoResDTO(rs.getInt("id"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getString("content")));
    }

    // 수정
    public void updateTodo(int id, TodoReqDTO todoReqDTO){
        String updateTodoQuery = "update Todo set createdAt = ? content = ? where id = ?";
        this.jdbcTemplate.update(updateTodoQuery, todoReqDTO.getCreatedAt(), todoReqDTO.getContent(), id);
    }

    // 삭제
    public void deleteTodo(int id){
        String deleteTodoQuery = "delete from Todo where id = ?";
        this.jdbcTemplate.update(deleteTodoQuery, id);
    }
}
