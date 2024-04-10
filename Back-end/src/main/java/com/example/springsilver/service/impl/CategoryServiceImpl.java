package com.example.springsilver.service.impl;

import com.example.springsilver.models.Category;
import com.example.springsilver.repository.CategoryRepository;
import com.example.springsilver.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(String categoryName) {
        Category category = new Category(categoryName);

        return categoryRepository.save(category);
    }
}
