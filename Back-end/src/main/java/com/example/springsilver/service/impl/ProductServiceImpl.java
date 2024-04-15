package com.example.springsilver.service.impl;

import com.example.springsilver.models.Category;
import com.example.springsilver.models.Product;
import com.example.springsilver.models.dto.ProductDto;
import com.example.springsilver.models.exceptions.CategoryNotFoundException;
import com.example.springsilver.models.exceptions.ProductNotFoundException;
import com.example.springsilver.repository.CategoryRepository;
import com.example.springsilver.repository.ProductRepository;
import com.example.springsilver.service.ProductService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Optional<Product> save(String name, String description, float price, Long categoryId, Long quantity) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundException(categoryId));

        this.productRepository.deleteByName(name);
        Product product=new Product(quantity, name, description, price,category);
        this.productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> save(ProductDto productDto) {
        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
       // this.productRepository.deleteByName(productDto.getName());
        Product product = new Product(productDto.getQuantity(),productDto.getName(),productDto.getDescription(), productDto.getPrice(), category);
        this.productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> edit(Long id,String name,String description, float price, Long categoryId, Long quantity) {
        Product product=this.productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));

        product.setPrice(price);
        product.setName(name);

        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotFoundException(categoryId));
        product.setCategory(category);
        this.productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product> edit(Long id, ProductDto productDto) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setQuantity(productDto.getQuantity());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        product.setCategory(category);

        this.productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
