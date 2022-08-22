package com.slow.slowdelivery.elasticsearch.infrastructure.repository;

import com.slow.slowdelivery.elasticsearch.domain.SortingCriteria;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomSearchRepositoryImpl implements CustomSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;
    private final RestHighLevelClient restHighLevelClient;

    @Override
    public List<Object> findByKeyword(String keyword, String sortingCriteria) throws IOException {
        // 검색 (가게명, 메뉴명, 카테고리)
        QueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.multiMatchQuery(keyword, "shopName", "menu.menuName","category"));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(boolQueryBuilder);

        // 정렬
        if(sortingCriteria.equals(SortingCriteria.BASIC.getCriteria()))
            searchSourceBuilder.sort(new FieldSortBuilder("shopId").order(SortOrder.ASC));
        if(sortingCriteria.equals(SortingCriteria.RATING.getCriteria()))
            searchSourceBuilder.sort(new FieldSortBuilder("rating").order(SortOrder.DESC));

        SearchRequest searchRequest = new SearchRequest().source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();


        List<Object> searchResponseList = new ArrayList<>();

        for (SearchHit hit : hits) {
            searchResponseList.add(hit.getSourceAsMap());
        }

        return searchResponseList;


    }
}
