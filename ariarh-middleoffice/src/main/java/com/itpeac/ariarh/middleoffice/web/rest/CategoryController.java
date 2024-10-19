package com.itpeac.ariarh.middleoffice.web.rest;

import com.itpeac.ariarh.middleoffice.domain.Category;
import com.itpeac.ariarh.middleoffice.domain.Offer;
import com.itpeac.ariarh.middleoffice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping()
    public ResponseEntity<?> getCategories(){
        try{
            List<Category> categoriesList = categoryService.getCategories();
            return ResponseEntity.ok(categoriesList);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping()
    ResponseEntity<?> createCategory(@RequestBody Category newCategory){
        System.out.println(newCategory.getLabel());
        System.out.println(newCategory);
        try {
            newCategory.setCreatedBy("system");
            newCategory.setLastModifiedBy("system");
            newCategory.setCreatedDate(Instant.now());
            newCategory.setLastModifiedDate(Instant.now());
            Category savedCategory = categoryService.saveCategory(newCategory);
            return ResponseEntity.ok(savedCategory);
        }catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{label}")
    public ResponseEntity<?> getCategoryWithQuestions(@PathVariable String label) {

        // Fetch the category from the database
        try {
            Category category = categoryService.getCategoryByLabel(label);
            return ResponseEntity.ok(category);
        }catch(Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }



}
