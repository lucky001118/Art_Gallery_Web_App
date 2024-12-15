package com.lucky.art.service;

import com.lucky.art.externalModel.Product;
import com.lucky.art.externalModel.User;
import com.lucky.art.model.Cart;
import com.lucky.art.model.CartItem;

public interface CartService {
    public Cart createCart(Cart cart);
    public CartItem addCrtItem(User user, Product product,String size,int quantity) throws Exception;
    public Cart findUserCart(User user) throws Exception;
}
