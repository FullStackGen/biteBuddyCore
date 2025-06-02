package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Address;
import com.bite.buddy.entity.Cart;
import com.bite.buddy.entity.Restaurant;
import com.bite.buddy.entity.User;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.AddressDto;
import com.bite.buddy.model.CartDto;
import com.bite.buddy.model.RestaurantDto;
import com.bite.buddy.repository.CartRepo;
import com.bite.buddy.repository.RestaurantRepo;
import com.bite.buddy.repository.UserRepo;
import com.bite.buddy.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final ModelMapper modelMapper;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;

    public CartServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDto createCart(Map<String, Object> requestMap) {
        CartDto dto = (CartDto) requestMap.get("cart");
        String userId = dto.getUserId();
        String restaurantId = dto.getRestaurantId();
        Cart entity = modelMapper.map(dto, Cart.class);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        entity.setCartId("CA." + n);
        User user = this.userRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Restaurant restaurant = this.restaurantRepo.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));
        entity.setUser(user);
        entity.setRestaurant(restaurant);
        Date date = new Date();
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        entity.setCreatedAt(localDateTime);
        return modelMapper.map(cartRepo.save(entity), CartDto.class);
    }

    @Override
    public CartDto updateCart(Map<String, Object> requestMap) {
        String id = requestMap.get("cartId").toString();
        CartDto dto = (CartDto) requestMap.get("cart");
        Cart entity = cartRepo.findByCartId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", id));
        Date date = new Date();
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        dto.setUpdatedAt(localDateTime);
        modelMapper.map(dto, entity);
        return modelMapper.map(cartRepo.save(entity), CartDto.class);
    }

    @Override
    public List<CartDto> getCartsByUser(Map<String, Object> requestMap) {
        String userId = requestMap.get("userId").toString();
        List<Cart> carts = this.cartRepo.findByUser_UserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", userId));
        return carts.stream().map(cart -> this.modelMapper.map(cart, CartDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteCart(Map<String, Object> requestMap) {
        String cartId = requestMap.get("cartId").toString();
        Cart cart = this.cartRepo.findByCartId(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));
        this.cartRepo.delete(cart);
    }
}
