package com.slow.slowdelivery.shop.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Getter
@Document(indexName = "shops")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Mapping(mappingPath = "elastic/shop-mapping.json")
@Setting(settingPath = "elastic/shop-setting.json")
public class ShopDocument {
    @Id
    private Long id;
    private String name;

    @Builder
    public ShopDocument(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
