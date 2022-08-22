package com.slow.slowdelivery.elasticsearch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortingCriteria {

    BASIC("basic", "기본순"),
    RATING("rating", "별점 높은순"),
    MIN_PRICE_DESC("min_price_desc", "최소주문금액 높은순"),
    MIN_PRICE_ASC("min_price_asc", "최소주문금액 낮은순"),

    ;

    private String criteria;
    private String description;
}
