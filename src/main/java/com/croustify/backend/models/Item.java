package com.croustify.backend.models;


import com.croustify.backend.enums.ItemCategorie;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article")
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ItemCategorie itemCategorie;
    @Column(name = "price")
    private Float price;
    private String pictureItem;
    @Column(name = "create_at")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updatedDate = LocalDateTime.now();

    /*
    //Relation ManyToOne avec Categorie car un item appartient à une catégorie
    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_picture", referencedColumnName = "id_picture")
    private Picture picture;
    */

    //Relation ManyToOne avec FoodTruck car un item appartient à un foodTruck
    @ManyToOne
    @JoinColumn(name = "id_food_truck")
    private FoodTruck foodTruck;


    public Item() {}

    public Item(Long id, String name, String description, ItemCategorie itemCategorie, Float price, String pictureItem, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemCategorie = itemCategorie;
        this.price = price;
        this.pictureItem = pictureItem;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemCategorie getItemCategorie() {
        return itemCategorie;
    }

    public void setItemCategorie(ItemCategorie itemCategorie) {
        this.itemCategorie = itemCategorie;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getPictureItem() {
        return pictureItem;
    }

    public void setPictureItem(String pictureItem) {
        this.pictureItem = pictureItem;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public FoodTruck getFoodTruck() {
        return foodTruck;
    }

    public void setFoodTruck(FoodTruck foodTruck) {
        this.foodTruck = foodTruck;
    }
}



