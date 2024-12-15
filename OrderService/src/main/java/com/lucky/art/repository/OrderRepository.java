package com.lucky.art.repository;

import com.lucky.art.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByUserId(Long userId);
    List<Orders> findBySellerId(Long sellerId);
}
