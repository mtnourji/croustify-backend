package com.croustify.backend.dto;

import java.util.List;

public class CategoryMenusDTO {

    private final MenuCategoryDTO menuCategory;
    private final List<MenuDTO> menus;

    public CategoryMenusDTO(MenuCategoryDTO categ, List<MenuDTO> list) {
        this.menuCategory = categ;
        this.menus = list;
    }

    public MenuCategoryDTO getMenuCategory() {
        return menuCategory;
    }

    public List<MenuDTO> getMenus() {
        return menus;
    }
}
