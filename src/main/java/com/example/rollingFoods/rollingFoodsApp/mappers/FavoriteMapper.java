package com.example.rollingFoods.rollingFoodsApp.mappers;

import com.example.rollingFoods.rollingFoodsApp.dto.FavoriteDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FavoriteMapper {
    FavoriteDTO favoriteToDto(Favorite favorite);
    Favorite dtoToFavorite(FavoriteDTO dto);
}
