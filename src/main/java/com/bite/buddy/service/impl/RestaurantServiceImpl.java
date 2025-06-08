package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Restaurant;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.RestaurantDto;
import com.bite.buddy.repository.RestaurantRepo;
import com.bite.buddy.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
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
    @Autowired
    private CacheManager cacheManager;

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
        String state = entity.getState();
        Cache cache = cacheManager.getCache("restaurantsByState");
        if (cache != null) {
            cache.evict(state);
        }
        return modelMapper.map(restaurantRepo.save(entity), RestaurantDto.class);
    }

    @Override
    public RestaurantDto updateRestaurant(Map<String, Object> requestMap) {
        String id = requestMap.get("restaurantId").toString();
        RestaurantDto dto = (RestaurantDto) requestMap.get("restaurant");
        Restaurant entity = restaurantRepo.findByRestaurantId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        modelMapper.map(dto, entity);
        String state = entity.getState();
        Cache cache = cacheManager.getCache("restaurantsByState");
        if (cache != null) {
            cache.evict(state);
        }
        return modelMapper.map(restaurantRepo.save(entity), RestaurantDto.class);
    }

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepo.findAll().stream()
                .map(r -> modelMapper.map(r, RestaurantDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRestaurant(Map<String, Object> requestMap) {
        String id = requestMap.get("restaurantId").toString();
        Restaurant entity = restaurantRepo.findByRestaurantId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        String state = entity.getState();
        restaurantRepo.delete(entity);
        Cache cache = cacheManager.getCache("restaurantsByState");
        if (cache != null) {
            cache.evict(state);
        }
    }

    @Override
    public RestaurantDto getRestaurantById(Map<String, Object> requestMap) {
        String id = requestMap.get("restaurantId").toString();
        Restaurant entity = restaurantRepo.findByRestaurantId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", id));
        return modelMapper.map(entity, RestaurantDto.class);
    }

    @Override
    @Cacheable(value = "restaurantsByState", key = "#requestMap['state']")
    public List<RestaurantDto> getRestaurantsByState(Map<String, Object> requestMap) {
        String state = requestMap.get("state").toString();
        List<Restaurant> entities = restaurantRepo.findByState(state)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "state", state));
        return entities.stream()
                .map(r -> modelMapper.map(r, RestaurantDto.class))
                .collect(Collectors.toList());
    }
}
