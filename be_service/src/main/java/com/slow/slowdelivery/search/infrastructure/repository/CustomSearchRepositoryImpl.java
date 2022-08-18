package com.slow.slowdelivery.search.infrastructure.repository;

import com.slow.slowdelivery.search.domain.SearchDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomSearchRepositoryImpl implements CustomSearchRepository {
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<SearchDocument> findByMenuName(String menuName) {

        Criteria criteria = Criteria.where("menu.menuName").contains(menuName);
        Query query = new CriteriaQuery(criteria);

        return elasticsearchOperations.search(query, SearchDocument.class)
                .stream().map(SearchHit::getContent).collect(Collectors.toList());

    }

    @Override
    public List<SearchDocument> findByShopName(String shopName) {

        Criteria criteria = Criteria.where("shopName").contains(shopName);
        Query query = new CriteriaQuery(criteria);
        return elasticsearchOperations.search(query, SearchDocument.class)
                .stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

}
