package com.bite.buddy.service;

import com.bite.buddy.model.ReviewDto;

import java.util.List;
import java.util.Map;

public interface ReviewService {

    ReviewDto addReview(Map<String, Object> requestMap);

    ReviewDto updateReview(Map<String, Object> requestMap);

    List<ReviewDto> getReviewsByKey(Map<String, Object> requestMap);

    void deleteReview(Map<String, Object> requestMap);
}