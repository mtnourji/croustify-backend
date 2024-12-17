package com.example.rollingFoods.rollingFoodsApp.services.imp;


import com.example.rollingFoods.rollingFoodsApp.dto.MenuDTO;
import com.example.rollingFoods.rollingFoodsApp.mappers.MenuMapper;
import com.example.rollingFoods.rollingFoodsApp.models.Menu;
import com.example.rollingFoods.rollingFoodsApp.repositories.MenuRepo;
import com.example.rollingFoods.rollingFoodsApp.services.MenuService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImp implements MenuService {

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private MenuMapper mapper;

    //
    public List<MenuDTO> getAllMenus() {
        final List<Menu> menus = menuRepo.findAll();
        return menus.stream().map(mapper::menuToDto).toList();
    }

    public MenuDTO getMenuById(Long id) {
        final Menu menu = menuRepo.findById(id).orElse(null);
        return mapper.menuToDto(menu);
    }

    public MenuDTO createMenu(MenuDTO menuDTO) {
        final Menu menu = mapper.dtoToMenu(menuDTO);
        final Menu savedMenu = menuRepo.save(menu);
        return mapper.menuToDto(savedMenu);
    }

    /*
    public MenuDTO getMenusByTruckId(Long foodTruckId) {
        final Menu menu = menuRepo.findByTruckId(foodTruckId).get(0);
        return mapper.menuToDto(menu);
    }

     */
}
