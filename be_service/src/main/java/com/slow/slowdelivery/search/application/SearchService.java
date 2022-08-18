package com.slow.slowdelivery.search.application;

import com.slow.slowdelivery.commons.exception.CustomException;
import com.slow.slowdelivery.commons.exception.ErrorCode;
import com.slow.slowdelivery.search.domain.SearchDocument;
import com.slow.slowdelivery.search.infrastructure.repository.SearchRepository;
import com.slow.slowdelivery.shop.domain.Menu;
import com.slow.slowdelivery.shop.domain.Shop;
import com.slow.slowdelivery.shop.infra.MenuRepository;
import com.slow.slowdelivery.shop.infra.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;
    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;

    // RDB에 있는 data -> ES DB에 저장
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
                    .menu(SearchDocument.toMenuDocument(menuListInSameShop)).build());
        }

        return searchRepository.findAll();
    }

    // 메뉴 검색
    public List<SearchDocument> searchMenu(String menuName){
        return searchRepository.findByMenuName(menuName);
    }

    // 가게 검색
    public List<SearchDocument> searchShop(String shopName){
        return searchRepository.findByShopName(shopName);
    }


}

