package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.PackageFullDto;
import com.tea.paradise.dto.ProductShortDto;
import com.tea.paradise.model.Package;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class PackageMapper {
    @Autowired
    VariantMapper variantMapper;
    @Autowired
    ProductMapper productMapper;

    @Mapping(source = "product", target = "product")
    @Mapping(source = "pack.id", target = "id")
    public abstract PackageFullDto toFullDto(Package pack,
                                             ProductShortDto product);

    public PackageFullDto mapFullDto(Package pack, Long userId) {
        ProductShortDto productShortDto = productMapper.mapShortDto(pack.getProduct(), userId);
        return toFullDto(pack, productShortDto);
    }
}
