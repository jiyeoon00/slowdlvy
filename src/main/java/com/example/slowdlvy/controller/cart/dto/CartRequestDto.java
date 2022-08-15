package com.example.slowdlvy.controller.cart.dto;

import com.example.slowdlvy.domain.cart.Cart;
import com.example.slowdlvy.domain.cart.CartItemOption;
import com.example.slowdlvy.domain.cart.CartItemOptionGroup;
import com.example.slowdlvy.domain.cart.CartLineItem;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class CartRequestDto {

    @Getter
    public static class addCart{
        private Long shopId;
        private Long memberId;
        private Long menuId;
        private String menuName;
        private int menuPrice;
        private int menuQuantity;
        private List<addCartItemOptionGroup> addCartItemOptionGroups;

        public Cart toCart(){
            return new Cart(shopId,memberId);
        }

        public CartLineItem toCartLineItem(){
            return new CartLineItem(menuName, menuPrice, menuQuantity, addCartItemOptionGroups.stream().map(a->a.toCartItemOptionGroup()).collect(Collectors.toList()));
        }
    }

    @Getter
    public static class addCartItemOptionGroup{
        private String groupName;
        private List<addCartItemOption> addCartItemOptions;

        public CartItemOptionGroup toCartItemOptionGroup(){
            return new CartItemOptionGroup(groupName, addCartItemOptions.stream().map(b->b.toCartItemOption()).collect(Collectors.toList()));
        }
    }

    @Getter
    public static class addCartItemOption{
        private String optionName;
        private int optionPrice;

        public CartItemOption toCartItemOption(){
            return new CartItemOption(this.optionName, this.optionPrice);
        }

    }
}
