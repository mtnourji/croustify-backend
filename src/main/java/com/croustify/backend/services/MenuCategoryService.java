package com.croustify.backend.services;

import com.croustify.backend.dto.MenuCategoryDTO;
import com.croustify.backend.mappers.MenuCategoryMapper;
import com.croustify.backend.models.MenuCategory;
import com.croustify.backend.repositories.MenuCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuCategoryService {
    private final MenuCategoryRepository categoryRepository;
    private final MenuCategoryMapper categoryMapper;

    @Autowired
    public MenuCategoryService(MenuCategoryRepository categoryRepository, MenuCategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<MenuCategoryDTO> getAllCategories() {
        return categoryMapper.categoryToDto(categoryRepository.findAll());
    }

    public void deleteCategory(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public MenuCategoryDTO createCategory(MenuCategoryDTO newCategory) {
        final MenuCategory categoryToCreate = categoryMapper.dtoToCategory(newCategory);
        final MenuCategory savedCategory = categoryRepository.save(categoryToCreate);
        return categoryMapper.categoryToDto(savedCategory);
    }
}
