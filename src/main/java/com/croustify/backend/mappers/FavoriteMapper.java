package com.croustify.backend.mappers;

import com.croustify.backend.dto.FavoriteDTO;
import com.croustify.backend.models.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FavoriteMapper {
    FavoriteDTO favoriteToDto(Favorite favorite);
    Favorite dtoToFavorite(FavoriteDTO dto);
}
