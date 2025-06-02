package com.bite.buddy.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private String cartItemId;
    private String cartId;
    private String menuId;
    private int quantity;
    private String notes;
}

