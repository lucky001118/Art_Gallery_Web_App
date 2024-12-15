package com.lucky.art.repository;

import com.lucky.art.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    Wishlist findByUserId(Long userId);
}
