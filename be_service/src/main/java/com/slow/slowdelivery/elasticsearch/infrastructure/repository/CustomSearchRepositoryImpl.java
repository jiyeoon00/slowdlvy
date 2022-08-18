package com.slow.slowdelivery.elasticsearch.infrastructure.repository;

import com.slow.slowdelivery.elasticsearch.domain.SearchDocument;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomSearchRepositoryImpl implements CustomSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<SearchDocument> findByKeyword(String keyword){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.multiMatchQuery(keyword, "shopName", "menu.menuName"));

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder).build();

        return elasticsearchOperations.search(nativeSearchQuery, SearchDocument.class)
                .stream().map(SearchHit::getContent).collect(Collectors.toList());

    }
}
