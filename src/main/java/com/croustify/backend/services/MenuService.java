package com.croustify.backend.services;

import com.croustify.backend.dto.MenuCreationDTO;
import com.croustify.backend.dto.MenuDTO;

import java.util.List;

public interface MenuService {

    List<MenuDTO> getFoodTruckMenus(long foodTruckId);
    MenuDTO getMenuById(Long id);
    MenuDTO createMenu(Long foodTruckId, MenuCreationDTO menuDTO);
}
