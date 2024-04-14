package com.example.springsilver.service;

import com.example.springsilver.models.Category;
import com.example.springsilver.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findByName(String name);
    Optional<Product> findById(Long id);
    Optional<Product> save(String name, String description, float price, Long categoryId, Long quantity);
    Optional<Product> edit(Long id,String name,String description, float price, Long categoryId, Long quantity);

    void deleteById(Long id);

}