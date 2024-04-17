package com.example.springsilver.service.impl;

import com.example.springsilver.models.Category;
import com.example.springsilver.models.dto.CategoryDto;
import com.example.springsilver.models.exceptions.CategoryNotFoundException;
import com.example.springsilver.repository.CategoryRepository;
import com.example.springsilver.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    //find by id
    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }

    //find by name
/*
    @Override
    public Optional<Category> findByName(String categoryName) {
        return this.categoryRepository.findByName(categoryName);
    }*/

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategories(String searchText) {
        List<Category> categories = categoryRepository.findAllByCategoryNameLike(searchText);
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException.CategoryNameNotFoundException("No categories found for search text: " + searchText);
        }
        return categories;
    }

   /* public List<Category> searchCategories(String searchText) {
        return categoryRepository.findAllByCategoryNameLike(searchText);
    }*/


    //create with Dto
    @Override
    public Optional<Category> save(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getCategoryName());
        this.categoryRepository.save(category);
        return Optional.of(category);
    }

    @Override
    public Category save(String categoryName) {
        Category category = new Category(categoryName);
        return categoryRepository.save(category);
    }

    //Update
    @Override
    public Optional<Category> edit(Long id, CategoryDto categoryDto) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        category.setCategoryName(categoryDto.getCategoryName());
        this.categoryRepository.save(category);
        return Optional.of(category);
    }

    //Delete
    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }
}


//C R U D