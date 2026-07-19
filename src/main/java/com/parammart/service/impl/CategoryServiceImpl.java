package com.parammart.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parammart.entity.Category;
import com.parammart.repository.CategoryRepository;
import com.parammart.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository repository;


    public CategoryServiceImpl(CategoryRepository repository){
        this.repository = repository;
    }


    @Override
    public Category createCategory(Category category){

        return repository.save(category);
    }


    @Override
    public List<Category> getAllCategories(){

        return repository.findAll();
    }


    @Override
    public Category getCategoryById(Long id){

        return repository.findById(id)
                .orElseThrow(
                 () -> new RuntimeException("Category not found")
                );
    }


    @Override
    public Category updateCategory(
            Long id,
            Category category){

        Category existing =
                getCategoryById(id);


        existing.setName(category.getName());
        existing.setDescription(category.getDescription());


        return repository.save(existing);
    }


    @Override
    public void deleteCategory(Long id){

        repository.deleteById(id);
    }

}