package com.croustify.backend.services;

import com.croustify.backend.dto.CategoryDTO;
import com.croustify.backend.mappers.CategoryMapper;
import com.croustify.backend.models.Category;
import com.croustify.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.categoryToDto(categoryRepository.findAll());
    }

    public void deleteCategory(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public CategoryDTO createCategory(CategoryDTO newCategory) {
        final Category categoryToCreate = categoryMapper.dtoToCategory(newCategory);
        final Category savedCategory = categoryRepository.save(categoryToCreate);
        return categoryMapper.categoryToDto(savedCategory);
    }
}
