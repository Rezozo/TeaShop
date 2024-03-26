package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.VariantDto;
import com.tea.paradise.model.Variant;
import org.mapstruct.Mapper;

@Mapper
public interface VariantMapper {
    VariantDto toDto(Variant variant);
}
