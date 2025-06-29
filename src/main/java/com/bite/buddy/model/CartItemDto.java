package com.bite.buddy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private String cartItemIdentifier;
    private String cartId;
    private String menuId;
    private int quantity;
    private String notes;
}