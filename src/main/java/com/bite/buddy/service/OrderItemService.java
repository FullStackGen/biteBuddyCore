package com.bite.buddy.service;

import com.bite.buddy.model.OrderItemDto;

import java.util.List;
import java.util.Map;

public interface OrderItemService {

    OrderItemDto addOrderItem(Map<String, Object> requestMap);

    List<OrderItemDto> getItemsByOrder(Map<String, Object> requestMap);
}