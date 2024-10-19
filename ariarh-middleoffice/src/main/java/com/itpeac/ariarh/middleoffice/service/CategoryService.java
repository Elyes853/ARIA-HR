package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public Category saveCategory(Category category);

    public List<Category> getCategories();

   Category getCategoryById(Long categoryId);

    Category getCategoryByLabel(String categoryId);

}
