package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDto {
    private String menuId;
    private String restaurantId;
    private String itemName;
    private String description;
    private Double price;
    private String category;
}
