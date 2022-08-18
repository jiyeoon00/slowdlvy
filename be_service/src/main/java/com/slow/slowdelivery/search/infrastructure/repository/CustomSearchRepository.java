package com.slow.slowdelivery.search.infrastructure.repository;

import java.util.List;

public interface CustomSearchRepository {
    List findByMenuName(String menuName);
    List findByShopName(String shopName);
}

