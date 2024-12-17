package com.example.rollingFoods.rollingFoodsApp.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {


    //for image upload and download food truck
    registry
            .addResourceHandler("/images/foodTruck/**")
            .addResourceLocations("file:///D:/Projet rollingFoodsApp/pictures/foodTruck/");

    //for image upload and download foods
    registry
            .addResourceHandler("/images/foods/**")
            .addResourceLocations("file:///D:/Projet rollingFoodsApp/pictures/foods/");
    }
}
