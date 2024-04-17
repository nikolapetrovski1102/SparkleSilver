package com.example.springsilver.service;

import com.example.springsilver.models.Category;
import com.example.springsilver.models.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    //create with Dto
    Optional<Category> save(CategoryDto categoryDto);

    Category save(String categoryName);

    //Update
    Optional<Category> edit(Long id, CategoryDto categoryDto);

    //Delete
    void deleteById(Long id);
}
