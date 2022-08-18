package com.slow.slowdelivery.search.presentation;

import com.slow.slowdelivery.commons.exception.CustomException;
import com.slow.slowdelivery.commons.exception.ErrorCode;
import com.slow.slowdelivery.search.application.SearchService;
import com.slow.slowdelivery.search.domain.SearchDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping ("/searchMenu")
    public List<SearchDocument> searchMenuDocs(@RequestParam String name){
        List<SearchDocument> searchDocuments = searchService.searchMenu(name);
        if(searchDocuments.isEmpty())
            throw new CustomException(ErrorCode.MENU_DOCS_NOT_FOUND);
        return searchDocuments;
    }

    @GetMapping("/searchShop")
    public List<SearchDocument> searchShopDocs(@RequestParam String name){
        List<SearchDocument> searchDocuments = searchService.searchShop(name);
        if(searchDocuments.isEmpty())
            throw new CustomException(ErrorCode.SHOP_DOCS_NOT_FOUND);
        return searchDocuments;
    }

}
