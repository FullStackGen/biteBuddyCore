package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RestaurantDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String restaurantId;
    private String restaurantName;
    private String location;
    private String city;
    private String zip;
    private String state;
    private String cuisine;
    private Double rating;
    private String status;
    private List<MenuDto> menuItems;
}
