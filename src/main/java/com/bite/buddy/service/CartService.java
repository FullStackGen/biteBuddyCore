package com.bite.buddy.service;

import com.bite.buddy.model.CartDto;

import java.util.List;
import java.util.Map;

public interface CartService {

    CartDto createCart(Map<String, Object> requestMap);

    CartDto updateCart(Map<String, Object> requestMap);

    List<CartDto> getCartsByUser(Map<String, Object> requestMap);

    void deleteCart(Map<String, Object> requestMap);
}