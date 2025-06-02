package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String reviewId;
    private String userId;
    private String restaurantId;
    private Integer rating;
    private String comment;
}

