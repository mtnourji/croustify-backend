package com.example.rollingFoods.rollingFoodsApp.config;




import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Demo Swagger API", version = "1.0", description = "Documentation of Demo Swagger API v1.0"))
public class SwaggerConfig {
}
