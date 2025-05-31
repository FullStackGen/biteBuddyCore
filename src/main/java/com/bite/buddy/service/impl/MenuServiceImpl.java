package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Menu;
import com.bite.buddy.entity.Restaurant;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.MenuDto;
import com.bite.buddy.repository.MenuRepo;
import com.bite.buddy.repository.RestaurantRepo;
import com.bite.buddy.service.MenuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    private final ModelMapper modelMapper;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;

    public MenuServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MenuDto addMenuItem(Map<String, Object> requestMap) {
        MenuDto dto = (MenuDto) requestMap.get("menu");
        String restaurantId = dto.getRestaurantId();
        Restaurant restaurant = restaurantRepo.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        Menu menu = modelMapper.map(dto, Menu.class);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        menu.setMenuId("ME." + n);
        menu.setRestaurant(restaurant);
        return modelMapper.map(menuRepo.save(menu), MenuDto.class);
    }

    @Override
    public MenuDto updateMenuItem(Map<String, Object> requestMap) {
        String menuId = requestMap.get("menuId").toString();
        MenuDto dto = (MenuDto) requestMap.get("menu");
        Menu menu = menuRepo.findByMenuId(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", menuId));
        modelMapper.map(dto, menu);
        return modelMapper.map(menuRepo.save(menu), MenuDto.class);
    }

    @Override
    public List<MenuDto> getMenuByRestaurant(Map<String, Object> requestMap) {
        String restaurantId = requestMap.get("restaurantId").toString();
        List<Menu> menus = menuRepo.findByRestaurant_RestaurantId(restaurantId);
        return menus.stream()
                .map(menu -> modelMapper.map(menu, MenuDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMenuItem(Map<String, Object> requestMap) {
        String menuId = requestMap.get("menuId").toString();
        Menu menu = menuRepo.findByMenuId(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", menuId));
        menuRepo.delete(menu);
    }
}
