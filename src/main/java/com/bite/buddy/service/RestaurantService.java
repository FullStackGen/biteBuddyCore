package com.bite.buddy.service;

import com.bite.buddy.model.RestaurantDto;

import java.util.List;
import java.util.Map;

public interface RestaurantService {

    RestaurantDto addRestaurant(Map<String, Object> requestMap);

    RestaurantDto updateRestaurant(Map<String, Object> requestMap);

    List<RestaurantDto> getAllRestaurants();

    RestaurantDto getRestaurantById(Map<String, Object> requestMap);

    void deleteRestaurant(Map<String, Object> requestMap);

    List<RestaurantDto> getRestaurantsByState(Map<String, Object> requestMap);
}

