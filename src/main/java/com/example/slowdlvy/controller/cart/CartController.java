package com.example.slowdlvy.controller.cart;

import com.example.slowdlvy.controller.cart.dto.CartRequestDto;
import com.example.slowdlvy.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("carts/{memberId}")
    public ResponseEntity addCartItem(@PathVariable Long memberId, @RequestBody CartRequestDto.addCart cartRequest){
        Long cartId = cartService.add(cartRequest);
        return ResponseEntity.ok(cartId);
        /**
         * 리턴시 장바구니_INFO 나타내주는 URL로 연결해주도록 추후 수정해주기
         */
    }
}