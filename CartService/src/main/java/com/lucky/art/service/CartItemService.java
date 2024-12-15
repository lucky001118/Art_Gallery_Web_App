package com.lucky.art.service;

import com.lucky.art.exception.ProductException;
import com.lucky.art.model.CartItem;

public interface CartItemService {
    CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws ProductException,Exception;
    void removeCartItem(Long userId, Long cartItemId) throws Exception;
    CartItem findCartItemById(Long id) throws Exception;
}
