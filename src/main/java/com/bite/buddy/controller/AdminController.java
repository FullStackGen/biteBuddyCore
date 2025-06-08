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
@RequestMapping("/biteBuddy/admin")
public class AdminController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/restaurants")
    public ResponseEntity<RestaurantDto> addRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurant", restaurantDto);
        RestaurantDto createdDto = this.restaurantService.addRestaurant(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@Valid @RequestBody RestaurantDto restaurantDto, @PathVariable String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurant", restaurantDto);
        requestMap.put("restaurantId", restaurantId);
        RestaurantDto updatedDto = this.restaurantService.updateRestaurant(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/restaurants/{restaurantId}")
    public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurantId", restaurantId);
        this.restaurantService.deleteRestaurant(requestMap);
        return ResponseEntity.ok(new ApiResponse("Restaurant deleted successfully", true));
    }

//    @GetMapping("restaurants")
//    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
//        return ResponseEntity.ok(this.restaurantService.getAllRestaurants());
//    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable("restaurantId") String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurantId", restaurantId);
        return ResponseEntity.ok(this.restaurantService.getRestaurantById(requestMap));
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurantByState(@RequestParam("state") String state) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("state", state);
        return ResponseEntity.ok(this.restaurantService.getRestaurantsByState(requestMap));
    }

    @PostMapping("/menus")
    public ResponseEntity<MenuDto> addMenu(@Valid @RequestBody MenuDto menuDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("menu", menuDto);
        MenuDto createdDto = this.menuService.addMenuItem(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/menus/{menuId}")
    public ResponseEntity<MenuDto> updateMenu(@Valid @RequestBody MenuDto menuDto, @PathVariable String menuId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("menu", menuDto);
        requestMap.put("menuId", menuId);
        MenuDto updatedDto = this.menuService.updateMenuItem(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<ApiResponse> deleteMenu(@PathVariable String menuId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("menuId", menuId);
        this.menuService.deleteMenuItem(requestMap);
        return ResponseEntity.ok(new ApiResponse("Menu deleted successfully", true));
    }

    @GetMapping("/restaurants/{restaurantId}/menus")
    public ResponseEntity<List<MenuDto>> getAllMenuByRestaurant(@PathVariable("restaurantId") String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurantId", restaurantId);
        return ResponseEntity.ok(this.menuService.getMenuByRestaurant(requestMap));
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<MenuDto> getAllMenuById(@PathVariable("menuId") String menuId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("menuId", menuId);
        return ResponseEntity.ok(this.menuService.getMenuById(requestMap));
    }
}