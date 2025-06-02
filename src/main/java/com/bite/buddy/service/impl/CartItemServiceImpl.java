package com.bite.buddy.service.impl;

import com.bite.buddy.entity.Cart;
import com.bite.buddy.entity.CartItem;
import com.bite.buddy.entity.Menu;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.model.CartItemDto;
import com.bite.buddy.repository.CartItemRepo;
import com.bite.buddy.repository.CartRepo;
import com.bite.buddy.repository.MenuRepo;
import com.bite.buddy.service.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final ModelMapper modelMapper;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private CartItemRepo cartItemRepo;

    public CartItemServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CartItemDto addCartItem(Map<String, Object> requestMap) {
        CartItemDto dto = (CartItemDto) requestMap.get("cartItem");
        String cartId = dto.getCartId();
        String menuId = dto.getMenuId();
        CartItem entity = modelMapper.map(dto, CartItem.class);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        entity.setCartItemId("CI." + n);
        Menu menu = this.menuRepo.findByMenuId(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", "id", menuId));
        Cart cart = this.cartRepo.findByCartId(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));
        entity.setMenuItem(menu);
        entity.setCart(cart);
        return modelMapper.map(cartItemRepo.save(entity), CartItemDto.class);
    }

    @Override
    public CartItemDto updateCartItem(Map<String, Object> requestMap) {
        String cartItemId = requestMap.get("cartItemId").toString();
        CartItemDto dto = (CartItemDto) requestMap.get("cartItem");
        CartItem cartItem = cartItemRepo.findByCartItemId(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", cartItemId));
        modelMapper.map(dto, cartItem);
        return modelMapper.map(cartItemRepo.save(cartItem), CartItemDto.class);
    }

    @Override
    public List<CartItemDto> getItemsByCart(Map<String, Object> requestMap) {
        String cartId = requestMap.get("cartId").toString();
        List<CartItem> cartItems = cartItemRepo.findByCart_CartId(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", cartId));
        return cartItems.stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCartItem(Map<String, Object> requestMap) {
        String cartItemId = requestMap.get("cartItemId").toString();
        CartItem cartItem = cartItemRepo.findByCartItemId(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", cartItemId));
        cartItemRepo.delete(cartItem);
    }
}
