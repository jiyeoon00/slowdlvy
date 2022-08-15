package com.example.slowdlvy.domain.cart;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemOption {

    @Id
    @Column(name = "CART_ITEM_OPTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ITEM_OPTION_GROUP_ID")
    private CartItemOptionGroup cartItemOptionGroup;

    public CartItemOption(String name, int price){
        this.name = name;
        this.price = price;
    }

    public void setCartItemOptionGroup(CartItemOptionGroup cartItemOptionGroup){
        this.cartItemOptionGroup = cartItemOptionGroup;
    }
}
