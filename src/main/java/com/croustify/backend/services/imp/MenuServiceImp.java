package com.croustify.backend.services.imp;


import com.croustify.backend.dto.MenuCreationDTO;
import com.croustify.backend.dto.MenuDTO;
import com.croustify.backend.mappers.MenuMapper;
import com.croustify.backend.models.FoodTruck;
import com.croustify.backend.models.Menu;
import com.croustify.backend.models.MenuCategory;
import com.croustify.backend.repositories.FoodTruckRepo;
import com.croustify.backend.repositories.MenuCategoryRepository;
import com.croustify.backend.repositories.MenuRepo;
import com.croustify.backend.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class MenuServiceImp implements MenuService {
    @Autowired
    private FoodTruckRepo foodTruckRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private MenuCategoryRepository menuCategoryRepository;
    @Autowired
    private MenuMapper mapper;

    @Transactional
    @Override
    public List<MenuDTO> getFoodTruckMenus(long foodTruckId) {
        final List<Menu> menus = menuRepo.findAllByFoodTruckId(foodTruckId);
        return menus.stream().map(mapper::menuToDto).toList();
    }

    @Transactional
    public MenuDTO getMenuById(Long id) {
        final Menu menu = menuRepo.findById(id).orElse(null);
        return mapper.menuToDto(menu);
    }

    @Transactional
    public MenuDTO createMenu(Long foodTruckId, MenuCreationDTO menuDTO) {
        final Menu menu = mapper.dtoToMenu(menuDTO);
        final FoodTruck foodTruck = foodTruckRepo.getReferenceById(foodTruckId);
        final List<MenuCategory> categs = menuCategoryRepository.findAllById(menuDTO.getCategories());
        menu.setFoodTruck(foodTruck);
        menu.setMenuCategories(new HashSet<>(categs));
        final Menu savedMenu = menuRepo.save(menu);
        return mapper.menuToDto(savedMenu);
    }
}
