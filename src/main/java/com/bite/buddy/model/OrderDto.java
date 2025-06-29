package com.bite.buddy.model;

import com.bite.buddy.configuration.ApplicationConstant;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto {
    private String orderId;
    private String userId;
    private String restaurantId;
    private Double totalPrice;
    private ApplicationConstant.OrderStatus status;
    private LocalDateTime timestamp;
}

