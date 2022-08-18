package com.slow.slowdelivery.elasticsearch.infrastructure.repository;

import com.slow.slowdelivery.elasticsearch.domain.SearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends ElasticsearchRepository<SearchDocument, Long>, CustomSearchRepository {
}

