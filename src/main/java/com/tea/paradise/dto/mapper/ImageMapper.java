package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.ImageDto;
import com.tea.paradise.model.Image;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {
   ImageDto toDto(Image image);
}
