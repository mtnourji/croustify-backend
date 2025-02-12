package com.croustify.backend.config;

import com.croustify.backend.repositories.FoodTruckRepo;
import com.croustify.backend.validation.OwnerUserAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${storage.trucks-image-location}")
    private String foodTruckPicturesLocation;

    @Autowired
    private FoodTruckRepo foodTruckRepo;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:///" + (foodTruckPicturesLocation.endsWith("/") ? foodTruckPicturesLocation : foodTruckPicturesLocation + "/");
        registry
                .addResourceHandler("/public-resources/agencies/**")
                .addResourceLocations(location + "agencies/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:8686", "http://192.168.0.115:8686", "http://localhost:8080", "http://localhost:4200")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OwnerUserAuthInterceptor(foodTruckRepo));
    }
}
