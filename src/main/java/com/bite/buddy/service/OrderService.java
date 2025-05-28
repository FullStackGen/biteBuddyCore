package com.bite.buddy.service;

import com.bite.buddy.model.OrderDto;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderDto createOrder(Map<String, Object> requestMap);

    OrderDto updateOrderStatus(Map<String, Object> requestMap);

    List<OrderDto> getOrdersByUser(Map<String, Object> requestMap);

    void cancelOrder(Map<String, Object> requestMap);
}

