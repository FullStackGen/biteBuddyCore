package com.bite.buddy.service;

import com.bite.buddy.model.MenuDto;

import java.util.List;
import java.util.Map;

public interface MenuService {

    MenuDto addMenuItem(Map<String, Object> requestMap);

    MenuDto updateMenuItem(Map<String, Object> requestMap);

    List<MenuDto> getMenuByRestaurant(Map<String, Object> requestMap);

    void deleteMenuItem(Map<String, Object> requestMap);

    MenuDto getMenuById(Map<String, Object> requestMap);
}

