package com.slow.slowdelivery.elasticsearch.domain;

import com.slow.slowdelivery.shop.domain.Menu;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Object field type
 * {“Doc id” : “ ”,
 * “Shop id” : “ “,
 * “Shop menu” : “ “,
 * “Menu”: [ {“menuId” : “ ”, “menuName”: “ “}, …]
 * }
 * */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Document(indexName = "slowdelivery")
public class SearchDocument {
    @Id
    private Long id;

    private Long shopId;
    private String shopName;

    @Field(type = FieldType.Object)
    private List<MenuDocument> menu;

    @Getter
    @Builder
    public static class MenuDocument{
        private Long menuId;
        private String menuName;
    }

    public static List<SearchDocument.MenuDocument> toMenuDocument(List<Menu> menuList){
        List<SearchDocument.MenuDocument> menuDocumentList = new ArrayList<>();
        for(Menu menu : menuList)
            menuDocumentList.add(SearchDocument.MenuDocument.builder()
                    .menuId(menu.getId())
                    .menuName(menu.getName())
                    .build());
        return menuDocumentList;

    }

}

