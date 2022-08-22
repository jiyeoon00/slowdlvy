package com.slow.slowdelivery.elasticsearch.infrastructure.repository;

import java.io.IOException;
import java.util.List;

public interface CustomSearchRepository {
    List<Object> findByKeyword(String keyword, String sortingCriteria) throws IOException;
}

