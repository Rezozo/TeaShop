package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.PackageShortDto;
import com.tea.paradise.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class PackageShortMapper {
    @Mapping(source = "pack.variant.title", target = "variantName")
    public abstract PackageShortDto toShortDto(Package pack);
}
