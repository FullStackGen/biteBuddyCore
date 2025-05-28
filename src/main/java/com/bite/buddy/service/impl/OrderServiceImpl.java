package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Order;
import com.bite.buddy.entity.Restaurant;
import com.bite.buddy.entity.User;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.OrderDto;
import com.bite.buddy.repository.OrderRepo;
import com.bite.buddy.repository.RestaurantRepo;
import com.bite.buddy.repository.UserRepo;
import com.bite.buddy.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;

    public OrderServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDto createOrder(Map<String, Object> requestMap) {
        OrderDto dto = (OrderDto) requestMap.get("order");
        String userId = requestMap.get("userId").toString();
        String restaurantId = requestMap.get("restaurantId").toString();
        User user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        Order order = modelMapper.map(dto, Order.class);
        order.setUser(user);
        order.setRestaurant(restaurant);
        Date date = new Date();
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        order.setTimestamp(localDateTime);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        order.setOrderId("OR." + n);
        return modelMapper.map(orderRepo.save(order), OrderDto.class);
    }

    @Override
    public OrderDto updateOrderStatus(Map<String, Object> requestMap) {
        String orderId = requestMap.get("orderId").toString();
        String status = requestMap.get("status").toString();
        Order order = orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        order.setStatus(status);
        return modelMapper.map(orderRepo.save(order), OrderDto.class);
    }

    @Override
    public List<OrderDto> getOrdersByUser(Map<String, Object> requestMap) {
        String userId = requestMap.get("userId").toString();
        List<Order> orders = orderRepo.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", userId));
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(Map<String, Object> requestMap) {
        String orderId = requestMap.get("orderId").toString();
        Order order = orderRepo.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        orderRepo.delete(order);
    }
}

