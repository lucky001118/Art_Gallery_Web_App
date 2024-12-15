package com.lucky.art.service.impl;

import com.lucky.art.externalModel.Product;
import com.lucky.art.externalModel.User;
import com.lucky.art.model.Wishlist;
import com.lucky.art.repository.WishlistRepository;
import com.lucky.art.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    @Override
    public Wishlist createWishlist(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setUserId(user.getId());
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByUserId(User user) {
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId());

        if (wishlist==null){
            wishlist=createWishlist(user);
        }
        return wishlist;
    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) {
        Wishlist wishlist = getWishlistByUserId(user);

        if (wishlist.getProductsIds().contains(product.getId())){
            wishlist.getProducts().remove(product);
            wishlist.getProductsIds().remove(product.getId());
        }else {
            wishlist.getProducts().add(product);
            wishlist.getProductsIds().add(product.getId());
        }
        return wishlistRepository.save(wishlist);
    }
}
