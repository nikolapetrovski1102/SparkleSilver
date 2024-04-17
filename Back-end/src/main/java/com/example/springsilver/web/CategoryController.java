package com.example.springsilver.web;

import com.example.springsilver.models.Category;
import com.example.springsilver.models.dto.CategoryDto;
import com.example.springsilver.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    List<Category> findAll() {
        return this.categoryService.findAll();
    }

    @GetMapping("/{id}") // oK
    public ResponseEntity<Category> findById(@PathVariable Long id){ //work
        return this.categoryService.findById(id).map(product -> ResponseEntity.ok().body(product))
                .orElseGet(()->ResponseEntity.badRequest().build());
    }


    @PostMapping("/add")
    public ResponseEntity<Category> create(@RequestParam String categoryName) {
        try {
            Category saved = this.categoryService.save(categoryName);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    //findByName : raboti -> http://localhost:9091/api/categories/search?searchText=example
    //samo exception ne raboti :todo
    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategories(@RequestParam("searchText") String searchText) {
        List<Category> categories = categoryService.searchCategories(searchText);
        return ResponseEntity.ok(categories);
    }

    //edit
    @PutMapping("/edit/{id}")
    public ResponseEntity<Category> edit(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        return this.categoryService.edit(id, categoryDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    //delete  : OK
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.categoryService.deleteById(id);
        if (this.categoryService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

}

//edit
//delete





