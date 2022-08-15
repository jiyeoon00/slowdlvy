package com.example.slowdlvy.domain.cart;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartLineItem {

    @Id
    @Column(name = "CART_LINE_ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @OneToMany(mappedBy = "cartLineItem", cascade = CascadeType.ALL)
    private List<CartItemOptionGroup> cartItemOptionGroups = new ArrayList<>();

    @Builder
    public CartLineItem(String name, int price, int quantity,List<CartItemOptionGroup> cartItemOptionGroups){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.cartItemOptionGroups.addAll(cartItemOptionGroups);
        cartItemOptionGroups.stream().forEach(a -> a.setCartLineItem(this));
    }

    public void setCart(Cart cart){
        this.cart = cart;
        cart.getCartLineItems().add(this);
    }
}
