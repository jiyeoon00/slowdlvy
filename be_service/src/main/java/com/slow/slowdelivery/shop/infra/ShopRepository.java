package com.slow.slowdelivery.shop.infra;

import com.slow.slowdelivery.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
