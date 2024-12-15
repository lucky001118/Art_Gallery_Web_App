package com.lucky.art.controller;

import com.lucky.art.externalModel.Product;
import com.lucky.art.externalModel.User;
import com.lucky.art.externalService.ProductService;
import com.lucky.art.externalService.UserService;
import com.lucky.art.model.Wishlist;
import com.lucky.art.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Wishlist> getWishlistByUserId(
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.getUserFromJwtToken(jwt);
        Wishlist wishlist = wishlistService.getWishlistByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishList(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) throws Exception{
        Product product = productService.getProductById(productId);
        User user = userService.getUserFromJwtToken(jwt);
        Wishlist updatedWishlist = wishlistService.addProductToWishlist(user,product);
        return ResponseEntity.ok(updatedWishlist);
    }
}
