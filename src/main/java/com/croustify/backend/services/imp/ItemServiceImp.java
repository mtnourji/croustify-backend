package com.croustify.backend.services.imp;

import com.croustify.backend.dto.ItemDTO;
import com.croustify.backend.enums.ItemCategorie;
import com.croustify.backend.repositories.CategorieRepo;
import com.croustify.backend.repositories.FoodTruckRepo;
import com.croustify.backend.repositories.ItemRepo;
import com.croustify.backend.mappers.ItemMapper;
import com.croustify.backend.models.FoodTruck;
import com.croustify.backend.models.Item;
import com.croustify.backend.services.ItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ItemServiceImp implements ItemService {

    @Value("D://Projet rollingFoodsApp/pictures/foods")
    private String foodItemsPicturesLocation;

    @Value("http://10.0.2.2:8686/api/images/foods/")
    private String foodItemsStacticRessourcesUrl;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemMapper mapper;

    @Autowired
    private CategorieRepo categorieRepo;

    @Autowired
    private FoodTruckRepo foodTruckRepo;

    public List<ItemDTO> getAllItems() {
        final List<Item> items = itemRepo.findAll();
        return items.stream().map(mapper::itemToDto).collect(Collectors.toList());
    }

    public Item addItem(Item item) {
        return itemRepo.save(item);
    }


    @Override
    public ItemDTO updateItem(Long id, ItemDTO itemDTO, MultipartFile file) throws IOException {
        final Item item = itemRepo.findById(id).orElseThrow(()->new RuntimeException("Item not found"));
        if(itemDTO.getName() != null) {
            item.setName(itemDTO.getName());
        }
        if(itemDTO.getDescription() != null) {
            item.setDescription(itemDTO.getDescription());
        }
        if(itemDTO.getPrice() != null) {
            item.setPrice(itemDTO.getPrice());
        }
        if(itemDTO.getItemCategorie() != null) {
            item.setItemCategorie(itemDTO.getItemCategorie());
        }
        if(file != null && !file.isEmpty()) {
            String imageUrl = uploadProfileImage(file, item.getId());
            item.setPictureItem(imageUrl);
        }else if(itemDTO.getPictureItem() != null) {
            item.setPictureItem(itemDTO.getPictureItem());
        }
        final Item updated = itemRepo.save(item);
        return mapper.itemToDto(updated);
    }

    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }

    public ItemDTO getItemById(Long id) {
        final Item item = itemRepo.findById(id).orElseThrow(()->new RuntimeException("Item not found"));
        return mapper.itemToDto(item);
    }



    @Override
    public Item addItemToCategorie(ItemDTO itemDTO) {
        return null;
    }






    //Add new item to food truck
    @Override
    public ItemDTO addItemToFoodTruck(final ItemDTO itemDTO, Long foodTruckId, MultipartFile file) throws IOException {
        final FoodTruck foodTruck = foodTruckRepo.findById(foodTruckId).orElseThrow(()->new RuntimeException("Food truck not found"));
        final Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setItemCategorie(itemDTO.getItemCategorie());
        item.setFoodTruck(foodTruck);

        final Item savedItem = itemRepo.save(item);



        if(file != null && !file.isEmpty()) {
            String imageUrl = uploadProfileImage(file, savedItem.getId());
            savedItem.setPictureItem(imageUrl);
            itemRepo.save(savedItem);
        }

        return mapper.itemToDto(savedItem);
    }
    //Get items by food truck id
    @Override
    public List<ItemDTO> getItemsByFoodTruckId(Long foodTruckId) {
        final List <Item> items = itemRepo.findByFoodTruckId(foodTruckId);
        return items.stream().map(mapper::itemToDto).collect(Collectors.toList());
    }

    //Get items by category
    @Override
    public List<ItemDTO> getItemsByCategory(String category) {
        ItemCategorie itemCategorieEnum = ItemCategorie.valueOf(category);  // Convertir la chaîne en enum
        List<Item> items = itemRepo.findByItemCategorie(itemCategorieEnum);
        return items.stream().map(mapper::itemToDto).collect(Collectors.toList());
    }

    //Get items by food truck id and category
    @Override
    public List<ItemDTO> getItemsByFoodTruckIdAndCategory(Long foodTruckId, String category) {
        ItemCategorie itemCategorieEnum = ItemCategorie.valueOf(category);  // Convertir la chaîne en enum
        List<Item> items = itemRepo.findByFoodTruckIdAndItemCategorie(foodTruckId, itemCategorieEnum);
        return items.stream().map(mapper::itemToDto).collect(Collectors.toList());
    }

    //Upload item image

    public String uploadProfileImage(MultipartFile file, Long itemId) throws io.jsonwebtoken.io.IOException, java.io.IOException {

        Item item = itemRepo.findById(itemId).orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + itemId));

        final Path locationPath = Paths.get(foodItemsPicturesLocation, String.valueOf(item.getId()));

        if(!Files.exists(locationPath)) {
            Files.createDirectory(locationPath);
        }

        try {
            final Path location = locationPath.resolve(StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
            item.setPictureItem(foodItemsStacticRessourcesUrl + item.getId() + "/" + StringUtils.cleanPath(file.getOriginalFilename()));
            itemRepo.save(item);
            return item.getPictureItem();
        } catch (io.jsonwebtoken.io.IOException e) {
            e.printStackTrace();
            throw new io.jsonwebtoken.io.IOException("Image upload failed");

        }

    }

}


