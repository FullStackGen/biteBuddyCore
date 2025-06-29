package com.bite.buddy.repository;

import com.bite.buddy.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, String> {

    Optional<Cart> findByCartId(String cartId);

    Optional<List<Cart>> findByUser_UserId(String userId);
}