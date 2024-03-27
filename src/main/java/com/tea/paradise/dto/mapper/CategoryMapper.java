package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.CategoryDto;
import com.tea.paradise.model.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}
