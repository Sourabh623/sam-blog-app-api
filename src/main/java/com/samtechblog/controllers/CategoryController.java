package com.samtechblog.controllers;

import com.samtechblog.payloads.APIResponse;
import com.samtechblog.payloads.CategoryDto;
import com.samtechblog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    //get category
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId)
    {
        CategoryDto categoryDto = this.categoryService.getCategoryById(catId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    //get all category
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    //create category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategoryDto, HttpStatus.CREATED);
    }

    //update category
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid  @PathVariable Integer catId, @RequestBody CategoryDto categoryDto)
    {
        CategoryDto updateCategoryDto = this.categoryService.updateCategory(categoryDto,catId);
        return new ResponseEntity<CategoryDto>(updateCategoryDto, HttpStatus.OK);
    }

    //delete category
    @DeleteMapping("/{catId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable("catId") Integer catId)
    {
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<APIResponse>(new APIResponse("Category is Deleted Successfully",true),HttpStatus.OK);
    }
}
