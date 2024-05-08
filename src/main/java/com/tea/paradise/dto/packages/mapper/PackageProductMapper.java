package com.tea.paradise.dto.packages.mapper;

import com.tea.paradise.dto.packages.PackageShortDto;
import com.tea.paradise.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class PackageProductMapper {
    @Mapping(source = "pack.variant.title", target = "variantType")
    public abstract PackageShortDto toShortDto(Package pack);
}
