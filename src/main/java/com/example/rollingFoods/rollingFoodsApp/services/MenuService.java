package com.example.rollingFoods.rollingFoodsApp.services;

import com.example.rollingFoods.rollingFoodsApp.dto.MenuDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Menu;

import java.util.List;

public interface MenuService {

    public List<MenuDTO> getAllMenus();
    public MenuDTO getMenuById(Long id);
    public MenuDTO createMenu(MenuDTO menuDTO);
    //public MenuDTO getMenusByTruckId(Long truckId);


}
