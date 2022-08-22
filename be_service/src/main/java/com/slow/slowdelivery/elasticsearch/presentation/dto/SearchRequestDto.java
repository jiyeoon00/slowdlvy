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
    private String sorter;

    public SearchDto toSearchDto(){
        return SearchDto.builder().keyword(keyword).sorter(sorter).build();
    }
}
