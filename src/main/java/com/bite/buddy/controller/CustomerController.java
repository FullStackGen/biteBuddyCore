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
@RequestMapping("/biteBuddy/customer")
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDto> addAddress(@Valid @RequestBody AddressDto addressDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("address", addressDto);
        AddressDto addedAddressDto = this.addressService.addAddress(requestMap);
        return new ResponseEntity<>(addedAddressDto, HttpStatus.CREATED);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable String addressId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("address", addressDto);
        requestMap.put("addressId", addressId);
        AddressDto updatedAddressDto = this.addressService.updateAddress(requestMap);
        return new ResponseEntity<>(updatedAddressDto, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable String addressId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("addressId", addressId);
        this.addressService.deleteAddress(requestMap);
        return ResponseEntity.ok(new ApiResponse("Address deleted successfully", true));
    }

    @GetMapping("/users/{userId}/addresses")
    public ResponseEntity<List<AddressDto>> getAddressesByUser(@PathVariable("userId") String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        return ResponseEntity.ok(this.addressService.getAddressesByUser(requestMap));
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewDto> addReview(@Valid @RequestBody ReviewDto reviewDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("review", reviewDto);
        ReviewDto createdDto = this.reviewService.addReview(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@Valid @RequestBody ReviewDto reviewDto, @PathVariable String reviewId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("review", reviewDto);
        requestMap.put("reviewId", reviewId);
        ReviewDto updatedDto = this.reviewService.updateReview(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable String reviewId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("reviewId", reviewId);
        this.reviewService.deleteReview(requestMap);
        return ResponseEntity.ok(new ApiResponse("Review deleted successfully", true));
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getAllReviewByKey(
            @RequestParam(value = "restaurantId", required = false) String restaurantId,
            @RequestParam(value = "userId", required = false) String userId) {

        if (restaurantId == null && userId == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "At least one should be not null"));
        }

        Map<String, Object> requestMap = new HashMap<>();
        if (restaurantId != null) requestMap.put("restaurantId", restaurantId);
        if (userId != null) requestMap.put("userId", userId);
        return ResponseEntity.ok(this.reviewService.getReviewsByKey(requestMap));
    }

    @PostMapping("/carts")
    public ResponseEntity<CartDto> addCart(@Valid @RequestBody CartDto cartDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cart", cartDto);
        CartDto createdDto = this.cartService.createCart(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/carts/{cartId}")
    public ResponseEntity<CartDto> updateCart(@Valid @RequestBody CartDto cartDto, @PathVariable String cartId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cart", cartDto);
        requestMap.put("cartId", cartId);
        CartDto updatedDto = this.cartService.updateCart(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable String cartId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartId", cartId);
        this.cartService.deleteCart(requestMap);
        return ResponseEntity.ok(new ApiResponse("Cart deleted successfully", true));
    }

    @GetMapping("/users/{userId}/carts")
    public ResponseEntity<List<CartDto>> getAllCartsByUser(@PathVariable("userId") String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        return ResponseEntity.ok(this.cartService.getCartsByUser(requestMap));
    }

    @PostMapping("/cart-items")
    public ResponseEntity<CartItemDto> addCartItem(@Valid @RequestBody CartItemDto cartItemDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartItem", cartItemDto);
        CartItemDto createdDto = this.cartItemService.addCartItem(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/cart-items/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItem(@Valid @RequestBody CartItemDto cartItemDto, @PathVariable String cartItemId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartItem", cartItemDto);
        requestMap.put("cartItemId", cartItemId);
        CartItemDto updatedDto = this.cartItemService.updateCartItem(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable String cartItemId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartItemId", cartItemId);
        this.cartItemService.deleteCartItem(requestMap);
        return ResponseEntity.ok(new ApiResponse("CartItem deleted successfully", true));
    }

    @GetMapping("/carts/{cartId}/cart-items")
    public ResponseEntity<List<CartItemDto>> getAllCartItemsByCart(@PathVariable("cartId") String cartId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("cartId", cartId);
        return ResponseEntity.ok(this.cartItemService.getItemsByCart(requestMap));
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("order", orderDto);
        OrderDto createdDto = this.orderService.createOrder(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@Valid @RequestBody OrderDto orderDto, @PathVariable String orderId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("order", orderDto);
        requestMap.put("orderId", orderId);
        OrderDto updatedDto = this.orderService.updateOrderStatus(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable String orderId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("orderId", orderId);
        OrderDto updatedDto = this.orderService.cancelOrder(requestMap);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<List<OrderDto>> getAllOrdersByUser(@PathVariable("userId") String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        return ResponseEntity.ok(this.orderService.getOrdersByUser(requestMap));
    }

    @PostMapping("/order-items")
    public ResponseEntity<OrderItemDto> addOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("orderItem", orderItemDto);
        OrderItemDto createdDto = this.orderItemService.addOrderItem(requestMap);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}/order-items")
    public ResponseEntity<List<OrderItemDto>> getAllOrderItemsByOrder(@PathVariable("orderId") String orderId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("orderId", orderId);
        return ResponseEntity.ok(this.orderItemService.getItemsByOrder(requestMap));
    }

    @PostMapping("/payments")
    public ResponseEntity<String> initiatePayment(@Valid @RequestBody PaymentDto paymentDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("paymentDto", paymentDto);
        requestMap.put("orderId", paymentDto.getOrderId());
        String redirectUrl = this.paymentService.initiatePayment(requestMap);
        return new ResponseEntity<>(redirectUrl, HttpStatus.CREATED);
    }
}