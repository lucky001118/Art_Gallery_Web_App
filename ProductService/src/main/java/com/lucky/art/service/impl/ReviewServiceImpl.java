package com.lucky.art.service.impl;

import com.lucky.art.externalModel.User;
import com.lucky.art.model.Product;
import com.lucky.art.model.Review;
import com.lucky.art.repository.ReviewRepository;
import com.lucky.art.request.CreateReviewRequest;
import com.lucky.art.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setUser(user);
        review.setUserId(user.getId());
        review.setProduct(product);
        review.setReviewText(req.getReviewText());
        review.setRating(req.getReviewRatting());
        review.setProductImages(req.getProductImages());

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId) {
        return null;
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) {
        return null;
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {

    }
}
