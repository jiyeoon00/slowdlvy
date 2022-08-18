package com.slow.slowdelivery.elasticsearch.infrastructure.repository;

import java.util.List;

public interface CustomSearchRepository {
    List findByKeyword(String keyword);
}

