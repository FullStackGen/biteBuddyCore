package com.bite.buddy.controller;

import com.bite.buddy.model.*;
import com.bite.buddy.service.*;
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
public class CustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    // POST
    @PostMapping("/auth/user/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("user", userDto);
        UserDto createdUserDto = this.userService.registerUser(requestMap);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/customer/user/modify/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("user", userDto);
        requestMap.put("userId", userId);
        UserDto updatedUserDto = this.userService.updateUser(requestMap);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/customer/user/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        this.userService.deleteUser(requestMap);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
    }

    // GET
    @GetMapping("/admin/user/search")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // GET
    @GetMapping("/customer/user/search/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String uid) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", uid);
        return ResponseEntity.ok(this.userService.getUser(requestMap));
    }

    @PostMapping("/customer/user/address/add")
    public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody AddressDto addressDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("address", addressDto);
        AddressDto addedAddressDto = this.addressService.addAddress(requestMap);
        return new ResponseEntity<>(addedAddressDto, HttpStatus.CREATED);
    }

    @PutMapping("/customer/user/address/modify/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable String addressId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("address", addressDto);
        requestMap.put("addressId", addressId);
        AddressDto updatedAddressDto = this.addressService.updateAddress(requestMap);
        return new ResponseEntity<>(updatedAddressDto, HttpStatus.OK);
    }

    @DeleteMapping("/customer/user/address/delete/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable String addressId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("addressId", addressId);
        this.addressService.deleteAddress(requestMap);
        return ResponseEntity.ok(new ApiResponse("Address deleted successfully", true));
    }

    @GetMapping("/customer/user/address/search/{userId}")
    public ResponseEntity<List<AddressDto>> getAllAddresses(@PathVariable("userId") String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        return ResponseEntity.ok(this.addressService.getAllAddress(requestMap));
    }

    @PostMapping("/customer/review/add")
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewDto reviewDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("review", reviewDto);
        ReviewDto createdDto = this.reviewService.addReview(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/customer/review/modify/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable String reviewId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("review", reviewDto);
        requestMap.put("reviewId", reviewId);
        ReviewDto updatedDto = this.reviewService.updateReview(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/customer/review/delete/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable String reviewId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("reviewId", reviewId);
        this.reviewService.deleteReview(requestMap);
        return ResponseEntity.ok(new ApiResponse("Review deleted successfully", true));
    }

    // GET
    @GetMapping("/customer/review/search/{restaurantId}")
    public ResponseEntity<List<ReviewDto>> getAllReviewByRestaurant(@PathVariable("restaurantId") String restaurantId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("restaurantId", restaurantId);
        return ResponseEntity.ok(this.reviewService.getReviewsByRestaurant(requestMap));
    }

    @PostMapping("/customer/cart/add")
    public ResponseEntity<CartDto> addCart(@Valid @RequestBody CartDto cartDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cart", cartDto);
        CartDto createdDto = this.cartService.createCart(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/customer/cart/modify/{cartId}")
    public ResponseEntity<CartDto> updateCart(@Valid @RequestBody CartDto cartDto, @PathVariable String cartId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cart", cartDto);
        requestMap.put("cartId", cartId);
        CartDto updatedDto = this.cartService.updateCart(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/customer/cart/delete/{cartId}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable String cartId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartId", cartId);
        this.cartService.deleteCart(requestMap);
        return ResponseEntity.ok(new ApiResponse("Cart deleted successfully", true));
    }

    // GET
    @GetMapping("/customer/cart/search/{userId}")
    public ResponseEntity<List<CartDto>> getAllCartsByUser(@PathVariable("userId") String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        return ResponseEntity.ok(this.cartService.getCartsByUser(requestMap));
    }

    @PostMapping("/customer/cart-item/add")
    public ResponseEntity<CartItemDto> addCartItem(@Valid @RequestBody CartItemDto cartItemDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartItem", cartItemDto);
        CartItemDto createdDto = this.cartItemService.addCartItem(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/customer/cart-item/modify/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@Valid @RequestBody CartItemDto cartItemDto, @PathVariable String cartItemId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartItem", cartItemDto);
        requestMap.put("cartItemId", cartItemId);
        CartItemDto updatedDto = this.cartItemService.updateCartItem(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/customer/cart-item/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable String cartItemId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartItemId", cartItemId);
        this.cartItemService.deleteCartItem(requestMap);
        return ResponseEntity.ok(new ApiResponse("CartItem deleted successfully", true));
    }

    // GET
    @GetMapping("/customer/cart-item/search/{cartId}")
    public ResponseEntity<List<CartItemDto>> getAllCartItemsByCart(@PathVariable("cartId") String cartId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartId", cartId);
        return ResponseEntity.ok(this.cartItemService.getItemsByCart(requestMap));
    }
}