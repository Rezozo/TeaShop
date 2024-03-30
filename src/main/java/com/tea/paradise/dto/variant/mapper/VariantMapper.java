package com.tea.paradise.dto.variant.mapper;

import com.tea.paradise.dto.variant.VariantDto;
import com.tea.paradise.model.Variant;
import org.mapstruct.Mapper;

@Mapper
public interface VariantMapper {
    VariantDto toDto(Variant variant);
}
