package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private String orderItemIdentifier;
    private String orderId;
    private String menuId;
    private Integer quantity;
}