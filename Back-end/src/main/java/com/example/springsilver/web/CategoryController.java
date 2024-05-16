package com.example.springsilver.web;

import com.example.springsilver.models.Category;
import com.example.springsilver.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll (){
        return this.categoryService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> create (@RequestParam String categoryName) {
        try {
            Category saved = this.categoryService.save(categoryName);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
