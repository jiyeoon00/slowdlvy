package com.slow.slowdelivery.elasticsearch.application;

import com.slow.slowdelivery.commons.exception.CustomException;
import com.slow.slowdelivery.commons.exception.ErrorCode;
import com.slow.slowdelivery.elasticsearch.application.dto.SearchDto;
import com.slow.slowdelivery.elasticsearch.domain.SearchDocument;
import com.slow.slowdelivery.elasticsearch.infrastructure.repository.SearchRepository;
import com.slow.slowdelivery.shop.domain.Menu;
import com.slow.slowdelivery.shop.domain.Shop;
import com.slow.slowdelivery.shop.infra.MenuRepository;
import com.slow.slowdelivery.shop.infra.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;

    // RDB에 있는 data -> ES document에 저장(동기화)
    @Transactional
    public Iterable<SearchDocument> saveData(){
        List<Shop> shopList = shopRepository.findAll();
        if(shopList.isEmpty()) throw new CustomException(ErrorCode.SHOP_NOT_FOUND);

        for(int i=0; i<shopList.size(); i++){
            List<Menu> menuListInSameShop = menuRepository.findAllMenuByShopId(shopList.get(i).getId());
            if(menuListInSameShop.isEmpty()) throw new CustomException(ErrorCode.MENU_NOT_FOUND);

            searchRepository.save(SearchDocument.builder()
                    .id(shopList.get(i).getId())
                    .shopId(shopList.get(i).getId())
                    .shopName(shopList.get(i).getName())
                    .minOrderPrice(shopList.get(i).getMinOrderPrice())
                    .category(shopList.get(i).getCategory())
                    .rating(shopList.get(i).getRating())
                    .menu(SearchDocument.toMenuDocument(menuListInSameShop))
                    .build());
        }

        return searchRepository.findAll();
    }

    // 가게/ 메뉴 통합 검색
    public List search(SearchDto searchDto) throws IOException {
        return searchRepository.findByKeyword(searchDto.getKeyword(), searchDto.getSorter());
    }


}

