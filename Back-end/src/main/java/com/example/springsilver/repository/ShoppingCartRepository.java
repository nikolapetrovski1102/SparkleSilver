package com.example.springsilver.repository;

import com.example.springsilver.models.Cart;
import com.example.springsilver.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUsers(Users users);
}