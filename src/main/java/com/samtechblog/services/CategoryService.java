package com.samtechblog.services;

import com.samtechblog.payloads.CategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto userDto);
    CategoryDto updateCategory(CategoryDto userDto, Integer catId);
    CategoryDto getCategoryById(Integer catId);
    List<CategoryDto> getAllCategories();
    void deleteCategory(Integer catId);
}
