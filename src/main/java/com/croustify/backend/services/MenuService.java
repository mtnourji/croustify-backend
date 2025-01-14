package com.croustify.backend.services;

import com.croustify.backend.dto.MenuDTO;

import java.util.List;

public interface MenuService {

    public List<MenuDTO> getAllMenus();
    public MenuDTO getMenuById(Long id);
    public MenuDTO createMenu(MenuDTO menuDTO);
    //public MenuDTO getMenusByTruckId(Long truckId);


}
