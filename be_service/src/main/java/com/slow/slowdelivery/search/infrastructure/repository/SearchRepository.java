package com.slow.slowdelivery.search.infrastructure.repository;

import com.slow.slowdelivery.search.domain.SearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends ElasticsearchRepository<SearchDocument, Long>, CustomSearchRepository {
}

