package com.slow.slowdelivery.shop.domain.search;

import com.slow.slowdelivery.shop.domain.ShopDocument;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomShopSearchRepository {
    List<ShopDocument> searchByName(String name, Pageable pageable);
}
