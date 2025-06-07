package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Restaurant;
import com.bite.buddy.entity.Review;
import com.bite.buddy.entity.User;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.ReviewDto;
import com.bite.buddy.repository.RestaurantRepo;
import com.bite.buddy.repository.ReviewRepo;
import com.bite.buddy.repository.UserRepo;
import com.bite.buddy.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ModelMapper modelMapper;
    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;

    public ReviewServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(value = "reviewsByRestaurant", key = "#dto.restaurantId")
    public ReviewDto addReview(Map<String, Object> requestMap) {
        ReviewDto dto = (ReviewDto) requestMap.get("review");
        String userId = dto.getUserId();
        String restaurantId = requestMap.get("restaurantId").toString();
        User user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        Review review = modelMapper.map(dto, Review.class);
        review.setUser(user);
        int noUser = reviewRepo.countByRestaurantId(restaurantId);
        double avgRating = (review.getRating() + restaurant.getRating()) / (noUser + 1);
        restaurant.setRating(avgRating);
        restaurantRepo.save(restaurant);
        review.setRestaurant(restaurant);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        review.setReviewId("RE." + n);
        return modelMapper.map(reviewRepo.save(review), ReviewDto.class);
    }

    @Override
    @CacheEvict(value = "reviewsByRestaurant", key = "#dto.restaurantId")
    public ReviewDto updateReview(Map<String, Object> requestMap) {
        String reviewId = requestMap.get("reviewId").toString();
        ReviewDto dto = (ReviewDto) requestMap.get("review");
        Review review = reviewRepo.findByReviewId(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        if (dto.getRating() != null) {
            Restaurant restaurant = review.getRestaurant();
            int noUser = reviewRepo.countByRestaurantId(restaurant.getRestaurantId());
            double avgRating = (dto.getRating() + restaurant.getRating() - review.getRating()) / (noUser);
            restaurant.setRating(avgRating);
            restaurantRepo.save(restaurant);
        }
        modelMapper.map(dto, review);
        return modelMapper.map(reviewRepo.save(review), ReviewDto.class);
    }

    @Override
    @Cacheable(value = "reviewsByRestaurant", key = "#requestMap['restaurantId']")
    public List<ReviewDto> getReviewsByRestaurant(Map<String, Object> requestMap) {
        String restaurantId = requestMap.get("restaurantId").toString();
        List<Review> reviews = reviewRepo.findByRestaurant_RestaurantId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", restaurantId));
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "reviewsByRestaurant", key = "#restaurantId")
    public void deleteReview(Map<String, Object> requestMap) {
        String reviewId = requestMap.get("reviewId").toString();
        Review review = reviewRepo.findByReviewId(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));
        if (review.getRating() != null) {
            Restaurant restaurant = review.getRestaurant();
            int noUser = reviewRepo.countByRestaurantId(restaurant.getRestaurantId());
            double avgRating = (restaurant.getRating() - review.getRating()) / (noUser - 1);
            restaurant.setRating(avgRating);
            restaurantRepo.save(restaurant);
        }
        String restaurantId = review.getRestaurant().getRestaurantId();
        reviewRepo.delete(review);
    }
}