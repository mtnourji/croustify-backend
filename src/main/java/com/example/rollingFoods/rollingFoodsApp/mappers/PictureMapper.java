package com.example.rollingFoods.rollingFoodsApp.mappers;


import com.example.rollingFoods.rollingFoodsApp.dto.PictureDTO;
import com.example.rollingFoods.rollingFoodsApp.models.Picture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PictureMapper {
    @Mapping(target = "id", ignore = true)
    Picture dtoToPicture(PictureDTO dto);
    PictureDTO pictureToDto(Picture picture);
}
