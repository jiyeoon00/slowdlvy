package com.slow.slowdelivery.elasticsearch.presentation;

import com.slow.slowdelivery.commons.exception.CustomException;
import com.slow.slowdelivery.commons.exception.ErrorCode;
import com.slow.slowdelivery.elasticsearch.application.SearchService;
import com.slow.slowdelivery.elasticsearch.domain.SearchDocument;
import com.slow.slowdelivery.elasticsearch.presentation.dto.SearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/")
    public ResponseEntity save(){
        Iterable<SearchDocument> searchDocuments = searchService.saveData();
        if(!searchDocuments.iterator().hasNext())
            throw new CustomException(ErrorCode.DOCS_NOT_FOUND);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/search")
    public List<SearchDocument> searchAll(@RequestBody SearchRequestDto searchRequestDto){
        List<SearchDocument> searchDocuments = searchService.search(searchRequestDto.toSearchDto());
        if(searchDocuments.isEmpty())
            throw new CustomException(ErrorCode.KEYWORD_NOT_FOUND);

        return searchDocuments;
    }

}
