package com.croustify.backend.mappers;


import com.croustify.backend.dto.PictureDTO;
import com.croustify.backend.models.Picture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PictureMapper {
    @Mapping(target = "id", ignore = true)
    Picture dtoToPicture(PictureDTO dto);
    PictureDTO pictureToDto(Picture picture);
}
