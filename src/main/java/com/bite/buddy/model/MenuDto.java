package com.bite.buddy.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MenuDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String menuId;
    private String restaurantId;
    private String itemName;
    private String description;
    private Double price;
    private String category;
    private boolean isAvailable;
}
