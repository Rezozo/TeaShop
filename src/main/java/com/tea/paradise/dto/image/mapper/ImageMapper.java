package com.tea.paradise.dto.image.mapper;

import com.tea.paradise.dto.image.ImageDto;
import com.tea.paradise.model.Image;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {
   ImageDto toDto(Image image);
   Image toModel(ImageDto imageDto);
}
