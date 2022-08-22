package com.slow.slowdelivery.shop.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Table(name = "shop")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @Column(name = "shop_name", nullable = false, unique = true)
    private String name;

    private int minOrderPrice;
    private String category;
    private float rating;


}
