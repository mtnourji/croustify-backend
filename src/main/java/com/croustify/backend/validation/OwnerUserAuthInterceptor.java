package com.croustify.backend.validation;

import com.croustify.backend.repositories.FoodTruckRepo;
import com.croustify.backend.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class OwnerUserAuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(OwnerUserAuthInterceptor.class);
    private final FoodTruckRepo foodTruckRepository;

    public OwnerUserAuthInterceptor(FoodTruckRepo foodTruckRepo) {
        this.foodTruckRepository = foodTruckRepo;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws IOException {
        if(handler instanceof HandlerMethod method){
            if(!isAllowedOwnUser(method, request, response)){
                return false;
            }
            return isAllowedOwnFoodTruck(method, request, response);
        }
        return true;
    }
    
    private boolean isAllowedOwnFoodTruck(
            final HandlerMethod method,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        OwnFoodTruck annotation = method.getMethodAnnotation(OwnFoodTruck.class);
        if(annotation != null){
            final String paramName = Optional.ofNullable(annotation.paramName()).orElse("foodTruckId");
            final Map<String, String> params = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            final String foodTruckId = Optional.ofNullable(params.get(paramName))
                    .orElseThrow(() -> new IllegalStateException("Wrong implementation of @OwnFoodTruck"));
            if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                logger.warn("User has tried to access information of foodTruck {} but is not authenticated", foodTruckId);
                return false;
            }
            final Long foodTruckIdLong = Long.valueOf(foodTruckId);
            if(!foodTruckRepository.existsByIdAndFoodTruckOwnerUserCredentialId(foodTruckIdLong, SecurityUtil.getConnectedUserOrThrow().getId())){
                logger.warn("User {} has tried to access information from foodTruck {}", SecurityUtil.getConnectedUserOrThrow().getId(), foodTruckId);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to access this resource");
                return false;
            }
        }
        return true;
    }
    private boolean isAllowedOwnUser(
            final HandlerMethod method, 
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        OwnUser annotation = method.getMethodAnnotation(OwnUser.class);
        if(annotation != null){
            final String paramName = Optional.ofNullable(annotation.paramName()).orElse("userId");
            final Map<String, String> params = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            final String userId = Optional.ofNullable(params.get(paramName))
                    .orElseThrow(() -> new IllegalStateException("Wrong implementation of @OwnUser"));
            if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                logger.warn("User has tried to access information of user {} but is not authenticated", userId);
                return false;
            }
            if(!SecurityUtil.getConnectedUserOrThrow().getId().equals(Long.valueOf(userId))){
                logger.warn("User {} has tried to access information from user {}", SecurityUtil.getConnectedUserOrThrow().getId(), userId);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to access this resource");
                return false;
            }
        }
        return true;
    }
}
