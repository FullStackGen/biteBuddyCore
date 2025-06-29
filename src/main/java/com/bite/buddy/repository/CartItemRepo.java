package com.bite.buddy.repository;

import com.bite.buddy.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, String> {

    Optional<List<CartItem>> findByCart_CartId(String cartId);

    Optional<CartItem> findByCartItemIdentifier(String cartItemId);
}