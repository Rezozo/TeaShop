package com.tea.paradise.dto.category.mapper;

import com.tea.paradise.dto.category.CategoryDto;
import com.tea.paradise.model.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDto toDto(Category category);
}
