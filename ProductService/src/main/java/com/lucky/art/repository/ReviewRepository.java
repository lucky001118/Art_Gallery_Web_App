package com.lucky.art.repository;

import com.lucky.art.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByProductId(Long productId);
}
