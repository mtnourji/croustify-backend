package com.croustify.backend.dto;

import com.croustify.backend.enums.ItemCategorie;

public class ItemDTO


{
    private Long id;
    private String name;
    private Float price;
    private String description;
    private String pictureItem;
    private ItemCategorie itemCategorie;




    public ItemDTO() {
    }

    public ItemDTO(Long id, String name, Float price, String description, String pictureItem, ItemCategorie itemCategorie) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.pictureItem = pictureItem;
        this.itemCategorie = itemCategorie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureItem() {
        return pictureItem;
    }

    public void setPictureItem(String pictureItem) {
        this.pictureItem = pictureItem;
    }

    public ItemCategorie getItemCategorie() {
        return itemCategorie;
    }

    public void setItemCategorie(ItemCategorie itemCategorie) {
        this.itemCategorie = itemCategorie;
    }


}
