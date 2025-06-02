package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestaurantDto {
    private String restaurantId;
    private String restaurantName;
    private String location;
    private String cuisine;
    private Double rating;
    private String status;
    private List<MenuDto> menuItems;
}
