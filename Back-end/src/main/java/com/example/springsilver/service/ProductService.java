package com.example.springsilver.service;

import com.example.springsilver.models.Category;
import com.example.springsilver.models.Product;
import com.example.springsilver.models.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findByName(String name);
    Optional<Product> findById(Long id);
    Optional<Product> save(String name, String description, float price, Long categoryId, Long quantity, String imagePathURL);
    Optional<Product> save(ProductDto productDto);
    Optional<Product> edit(Long id,String name,String description, float price, Long categoryId, Long quantity);
    Optional<Product> edit(Long id,ProductDto productDto);

    void deleteById(Long id);

}