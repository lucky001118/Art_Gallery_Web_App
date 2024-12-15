package com.lucky.art.repository;

import com.lucky.art.externalModel.Product;
import com.lucky.art.model.Cart;
import com.lucky.art.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByCartAndProductIdAndSize(Cart cart, Long productId,String size);
}
