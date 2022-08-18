package com.slow.slowdelivery.elasticsearch.application.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SearchDto {
    private String keyword;
}
