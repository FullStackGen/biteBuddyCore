package com.bite.buddy.repository;

import com.bite.buddy.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepo extends JpaRepository<Menu, String> {

    Optional<List<Menu>> findByRestaurant_RestaurantId(String restaurantId);

    Optional<Menu> findByMenuId(String menuId);
}