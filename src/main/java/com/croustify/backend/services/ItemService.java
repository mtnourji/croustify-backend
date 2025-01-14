package com.croustify.backend.services;

import com.croustify.backend.dto.ItemDTO;
import com.croustify.backend.models.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {



    public List<ItemDTO> getAllItems();
    public ItemDTO getItemById(Long id);
    public Item addItem(Item item);
    //Update item
    public ItemDTO updateItem(Long id, ItemDTO itemDTO, MultipartFile file) throws IOException;
    //Delete item
    public void deleteItem(Long id);
    //public List<ItemDTO> getItemsByCategorieId(Long categorieId);
    public Item addItemToCategorie(ItemDTO itemDTO);
    //public List<ItemDTO> getItemsByFoodTruckId(Long foodTruckId);







    //Add new item to food truck
    ItemDTO addItemToFoodTruck(ItemDTO itemDTO, Long foodTruckId, MultipartFile file) throws IOException;

    List<ItemDTO> getItemsByFoodTruckId(Long foodTruckId);
    List<ItemDTO> getItemsByCategory(String category);
    public List<ItemDTO> getItemsByFoodTruckIdAndCategory(Long foodTruckId, String category);
}
