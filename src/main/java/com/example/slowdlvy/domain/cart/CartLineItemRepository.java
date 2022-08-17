package com.example.slowdlvy.domain.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartLineItemRepository extends JpaRepository<CartLineItem, Long> {
}
