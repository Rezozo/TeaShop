package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.PackageProductDto;
import com.tea.paradise.dto.PackageShortDto;
import com.tea.paradise.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class PackageProductMapper {
    @Mapping(source = "pack.variant.title", target = "variantName")
    public abstract PackageShortDto toShortDto(Package pack);


    public abstract PackageProductDto toProductDto(Package pack);

    @Mapping(target = "buckets", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "variant.packages", ignore = true)
    public abstract Package mapToProductPackage(PackageProductDto packageProductDto);
}
