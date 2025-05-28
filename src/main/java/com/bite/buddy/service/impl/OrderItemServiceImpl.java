package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Menu;
import com.bite.buddy.entity.Order;
import com.bite.buddy.entity.OrderItem;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.OrderItemDto;
import com.bite.buddy.repository.MenuRepo;
import com.bite.buddy.repository.OrderItemRepo;
import com.bite.buddy.repository.OrderRepo;
import com.bite.buddy.service.OrderItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final ModelMapper modelMapper;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private MenuRepo menuRepo;

    public OrderItemServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderItemDto addOrderItem(Map<String, Object> requestMap) {
        OrderItemDto dto = (OrderItemDto) requestMap.get("orderItem");
        String orderId = requestMap.get("orderId").toString();
        String menuItemId = requestMap.get("menuItemId").toString();
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        Menu menu = menuRepo.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", menuItemId));
        OrderItem orderItem = modelMapper.map(dto, OrderItem.class);
        orderItem.setOrder(order);
        orderItem.setMenuItem(menu);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        orderItem.setOrderItemId("IT." + n);
        return modelMapper.map(orderItemRepo.save(orderItem), OrderItemDto.class);
    }

    @Override
    public OrderItemDto updateOrderItem(Map<String, Object> requestMap) {
        String orderItemId = requestMap.get("orderItemId").toString();
        OrderItemDto dto = (OrderItemDto) requestMap.get("orderItem");
        OrderItem orderItem = orderItemRepo.findByOrderItemId(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", orderItemId));
        modelMapper.map(dto, orderItem);
        return modelMapper.map(orderItemRepo.save(orderItem), OrderItemDto.class);
    }

    @Override
    public List<OrderItemDto> getItemsByOrder(Map<String, Object> requestMap) {
        String orderId = requestMap.get("orderId").toString();
        List<OrderItem> orderItems = orderItemRepo.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", orderId));
        return orderItems.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItem(Map<String, Object> requestMap) {
        String orderItemId = requestMap.get("orderItemId").toString();
        OrderItem orderItem = orderItemRepo.findByOrderItemId(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", orderItemId));
        orderItemRepo.delete(orderItem);
    }
}

