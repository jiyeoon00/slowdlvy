package com.slow.slowdelivery.elasticsearch.presentation.dto;

import com.slow.slowdelivery.elasticsearch.application.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDto {
    private String keyword;

    public SearchDto toSearchDto(){
        return SearchDto.builder().keyword(keyword).build();
    }
}
