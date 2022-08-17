package com.example.slowdlvy.service.cart;

import com.example.slowdlvy.controller.cart.dto.CartRequestDto;
import com.example.slowdlvy.domain.cart.Cart;
import com.example.slowdlvy.domain.cart.CartLineItem;
import com.example.slowdlvy.domain.cart.CartLineItemRepository;
import com.example.slowdlvy.domain.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartLineItemRepository cartLineItemRepository;

    /**
     * <장바구니 추가 과정>
     * 프론트단에서 요청전에 이미 존재하는 cart안의 shopId와 새로 추가하려는 아이템의 shopId가 다를때 장바구니 교체여부를 물어보고 사용자가 교체를 원한다면 요청
     * 1) 해당 member에 대한 cart가 존재하지 않는다면 새로운 카트 생성 후 아이템 추가
     * 2) cart가 이미 존재한다면 그 cart의 shopId와 비교를 해주고 같으면 그냥 기존 cart에 아이템 추가해주기, 다르면 cart삭제 후 새로운 카트 생성 후 아이템 추가
     */
    @Transactional
    public Long add(CartRequestDto.addCart cartRequest){
        Optional<Cart> cartOptional = cartRepository.findByMemberId(cartRequest.getMemberId());
        CartLineItem cartLineItem = cartRequest.toCartLineItem();

        if(cartOptional.isPresent()){
            Cart findCart = cartOptional.get();
            if(findCart.isEqualByShop(cartRequest.getShopId())){
                cartLineItem.setCart(findCart);
                cartLineItemRepository.save(cartLineItem);
                return findCart.getId();
            }else{
                cartRepository.delete(findCart);
            }
        }
        Cart newCart = cartRequest.toCart();
        cartLineItem.setCart(newCart);
        cartRepository.save(newCart);
        cartLineItemRepository.save(cartLineItem);
        return newCart.getId();
    }

    /**
     * <장바구니 삭제 과정>
     * CartLineItem 삭제 후에 만약 Cart가 담긴 상품이 없이 빈 상태라면 Cart도 삭제해준다.
     */
    @Transactional
    public void remove(Long cartLineItemId){
        cartLineItemRepository.deleteById(cartLineItemId);
    }

    @Transactional
    public void isEmptyRemove(Long cartId){
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if(cartOptional.isPresent()){
            if(cartOptional.get().isEmptyCart()){
                cartRepository.deleteById(cartId);
            }
        }
    }


}