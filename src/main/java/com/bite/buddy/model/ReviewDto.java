package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String reviwId;
    private String userId;
    private String restaurantId;
    private Integer rating;
    private String comment;
}

