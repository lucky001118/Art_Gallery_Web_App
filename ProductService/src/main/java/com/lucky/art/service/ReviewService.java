package com.lucky.art.service;

import com.lucky.art.externalModel.User;
import com.lucky.art.model.Product;
import com.lucky.art.model.Review;
import com.lucky.art.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req, User user, Product product);
    List<Review> getReviewByProductId(Long productId);
    Review updateReview(Long reviewId,String reviewText,double rating,Long userId);
    void deleteReview(Long reviewId,Long userId);
}
