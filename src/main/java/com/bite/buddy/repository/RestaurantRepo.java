package com.bite.buddy.repository;

import com.bite.buddy.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepo extends JpaRepository<Restaurant, String> {

    Optional<Restaurant> findByRestaurantId(String restaurantId);

    Optional<List<Restaurant>> findByState(String sate);
}