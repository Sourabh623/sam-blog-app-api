package com.samtechblog.services.impl;

import com.samtechblog.exceptions.ResourceNotFoundException;
import com.samtechblog.models.Category;
import com.samtechblog.payloads.CategoryDto;
import com.samtechblog.repositories.CategoryRepository;
import com.samtechblog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        //convert categoryDto to category
        Category category = this.dtoToCategory(categoryDto);
        category.setCategory_created_at(new Date());
        category.setCategory_updated_at(new Date());

        //save category in the db
        Category saveCategory = this.categoryRepository.save(category);

        //return the categoryDto object instance of the client
        return this.categoryToCategoryDto(saveCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {

        //getting the category by catId from db
        Category category = this.categoryRepository.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Id", catId));

        //set the userDto data into user
        category.setCategory_name(categoryDto.getCategory_name());
        category.setCategory_description(categoryDto.getCategory_description());
        category.setCategory_updated_at(new Date());

        //save the user in db
        Category updateCategory = this.categoryRepository.save(category);

        //convert category to categoryDto
        return this.categoryToCategoryDto(updateCategory);
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        //get the single category by id
        Category category =  this.categoryRepository.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Id", catId));

        //convert user to userDto and return to the client
        return this.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        //getting the users from the db
        List<Category> categories =  this.categoryRepository.findAll();

        //convert categories list into category dto using stream api
        return categories.stream().map(this::categoryToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Integer catId) {
        //getting the category by catId from db
        Category category = this.categoryRepository.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Id", catId));

        //delete the category into the db
        this.categoryRepository.delete(category);
    }

    //using model mapper lib to map categoryDto to category conversion here
    public Category dtoToCategory(CategoryDto categoryDto){
        return this.modelMapper.map(categoryDto, Category.class);  //map(source, destination)
    }

    //using model mapper lib to map category to categoryDto conversion here
    public CategoryDto categoryToCategoryDto(Category category){
        return this.modelMapper.map(category, CategoryDto.class);  //map(source, destination)
    }
}
