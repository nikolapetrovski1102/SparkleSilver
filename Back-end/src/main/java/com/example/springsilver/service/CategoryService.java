package com.example.springsilver.service;

import com.example.springsilver.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category save(String categoryName);

}
