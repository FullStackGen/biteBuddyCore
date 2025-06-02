package com.bite.buddy.service;

import com.bite.buddy.model.CartItemDto;

import java.util.List;
import java.util.Map;

public interface CartItemService {

    CartItemDto addCartItem(Map<String, Object> requestMap);

    CartItemDto updateCartItem(Map<String, Object> requestMap);

    List<CartItemDto> getItemsByCart(Map<String, Object> requestMap);

    void deleteCartItem(Map<String, Object> requestMap);
}

