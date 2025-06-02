package com.bite.buddy.controller;

import com.bite.buddy.model.ApiResponse;
import com.bite.buddy.model.MenuDto;
import com.bite.buddy.model.RestaurantDto;
import com.bite.buddy.service.MenuService;
import com.bite.buddy.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/biteBuddy")
public class AdminController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/admin/restaurant/add")
    public ResponseEntity<RestaurantDto> addRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurant", restaurantDto);
        RestaurantDto createdDto = this.restaurantService.addRestaurant(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/admin/restaurant/modify/{restaurantId}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@Valid @RequestBody RestaurantDto restaurantDto, @PathVariable String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurant", restaurantDto);
        requestMap.put("restaurantId", restaurantId);
        RestaurantDto updatedDto = this.restaurantService.updateRestaurant(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/admin/restaurant/delete/{restaurantId}")
    public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurantId", restaurantId);
        this.restaurantService.deleteRestaurant(requestMap);
        return ResponseEntity.ok(new ApiResponse("Restaurant deleted successfully", true));
    }

    // GET
    @GetMapping("/admin/restaurant/search")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        return ResponseEntity.ok(this.restaurantService.getAllRestaurants());
    }

    // GET
    @GetMapping("/admin/restaurant/search/{restaurantId}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable("restaurantId") String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurantId", restaurantId);
        return ResponseEntity.ok(this.restaurantService.getRestaurantById(requestMap));
    }

    @PostMapping("/admin/restaurant/menu/add")
    public ResponseEntity<MenuDto> addMenu(@Valid @RequestBody MenuDto menuDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("menu", menuDto);
        MenuDto createdDto = this.menuService.addMenuItem(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/admin/restaurant/menu/modify/{menuId}")
    public ResponseEntity<MenuDto> updateMenu(@Valid @RequestBody MenuDto menuDto, @PathVariable String menuId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("menu", menuDto);
        requestMap.put("menuId", menuId);
        MenuDto updatedDto = this.menuService.updateMenuItem(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/admin/restaurant/menu/delete/{menuId}")
    public ResponseEntity<ApiResponse> deleteMenu(@PathVariable String menuId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("menuId", menuId);
        this.menuService.deleteMenuItem(requestMap);
        return ResponseEntity.ok(new ApiResponse("Menu deleted successfully", true));
    }

    // GET
    @GetMapping("/admin/restaurant/menu/search/{restaurantId}")
    public ResponseEntity<List<MenuDto>> getAllMenuByRestaurant(@PathVariable("restaurantId") String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurantId", restaurantId);
        return ResponseEntity.ok(this.menuService.getMenuByRestaurant(requestMap));
    }
}