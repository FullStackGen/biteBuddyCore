package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ReviewDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String reviewId;
    private String userId;
    private String restaurantId;
    private Integer rating;
    private String comment;
}

