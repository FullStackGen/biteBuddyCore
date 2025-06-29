package com.bite.buddy.repository;

import com.bite.buddy.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, String> {

    Optional<List<Review>> findByRestaurant_RestaurantId(String restaurantId);

    Optional<List<Review>> findByUser_UserId(String userId);

    @Query("SELECT r FROM Review r WHERE r.restaurant.restaurantId = :restaurantId AND r.user.userId = :userId")
    Optional<List<Review>> findByRestaurantIdAndUserId(String restaurantId, String userId);

    Optional<Review> findByReviewId(String reviewId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.restaurant.restaurantId = :restaurantId")
    int countByRestaurantId(@Param("restaurantId") String restaurantId);
}