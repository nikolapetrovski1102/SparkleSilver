package com.example.springsilver.repository;

import com.example.springsilver.models.Cart;
import com.example.springsilver.models.Product;
import com.example.springsilver.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUsers(Users users);
    Cart findCartById (Long id);
}
