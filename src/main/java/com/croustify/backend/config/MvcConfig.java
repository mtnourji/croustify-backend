package com.croustify.backend.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    // TODO Use Properties
    @Override
    public void addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {

    registry
            .addResourceHandler("/images/foodTruck/**")
            .addResourceLocations("file:///D:/Projet rollingFoodsApp/pictures/foodTruck/");

    registry
            .addResourceHandler("/images/foods/**")
            .addResourceLocations("file:///D:/Projet rollingFoodsApp/pictures/foods/");
    }
}
