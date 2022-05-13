package com.example.pilotproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "todo")
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isComplete; // 완료 및 활성화

    // update
    public void update(String content){
        this.content = content;
    }
    public void update(Boolean isComplete){
        if(isComplete)
            this.isComplete = false;
        else
            this.isComplete = true;
    }

}

@Converter
class BooleanToYNConverter implements AttributeConverter<Boolean, String> {
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute!=null && attribute) ? "Y":"N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
