package com.bite.buddy.repository;

import com.bite.buddy.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, String> {
    Optional<List<Review>> findByRestaurant_RestaurantId(String restaurantId);

    Optional<Review> findByReviewId(String reviewId);
}
