package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Restaurant;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.RestaurantDto;
import com.bite.buddy.repository.RestaurantRepo;
import com.bite.buddy.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final ModelMapper modelMapper;
    @Autowired
    private RestaurantRepo restaurantRepo;

    public RestaurantServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RestaurantDto addRestaurant(Map<String, Object> requestMap) {
        RestaurantDto dto = (RestaurantDto) requestMap.get("restaurant");
        Restaurant entity = modelMapper.map(dto, Restaurant.class);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        entity.setRestaurantId("EA." + n);
        return modelMapper.map(restaurantRepo.save(entity), RestaurantDto.class);
    }

    @Override
    public RestaurantDto updateRestaurant(Map<String, Object> requestMap) {
        String id = requestMap.get("restaurantId").toString();
        RestaurantDto dto = (RestaurantDto) requestMap.get("restaurant");
        Restaurant entity = restaurantRepo.findByRestaurantId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        modelMapper.map(dto, entity);
        return modelMapper.map(restaurantRepo.save(entity), RestaurantDto.class);
    }

    @Override
    public List<RestaurantDto> getAllRestaurants(Map<String, Object> requestMap) {
        return restaurantRepo.findAll().stream()
                .map(r -> modelMapper.map(r, RestaurantDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRestaurant(Map<String, Object> requestMap) {
        String id = requestMap.get("restaurantId").toString();
        Restaurant entity = restaurantRepo.findByRestaurantId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        restaurantRepo.delete(entity);
    }

    @Override
    public RestaurantDto getRestaurantById(Map<String, Object> requestMap) {
        String id = requestMap.get("restaurantId").toString();
        Restaurant entity = restaurantRepo.findByRestaurantId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        return modelMapper.map(entity, RestaurantDto.class);
    }
}
