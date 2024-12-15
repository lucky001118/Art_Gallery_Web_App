package com.lucky.art.service;

import com.lucky.art.externalModel.Product;
import com.lucky.art.externalModel.User;
import com.lucky.art.model.Wishlist;

public interface WishlistService {
    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);
    Wishlist addProductToWishlist(User user, Product product);
}
