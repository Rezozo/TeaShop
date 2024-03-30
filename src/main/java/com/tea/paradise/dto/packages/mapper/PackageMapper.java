package com.tea.paradise.dto.packages.mapper;

import com.tea.paradise.dto.product.mapper.ProductMapper;
import com.tea.paradise.dto.saveDto.PackageSaveDto;
import com.tea.paradise.dto.variant.mapper.VariantMapper;
import com.tea.paradise.model.Package;
import com.tea.paradise.model.Product;
import com.tea.paradise.model.Variant;
import com.tea.paradise.repository.VariantRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class PackageMapper {
    @Autowired
    VariantMapper variantMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    VariantRepository variantRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "variant", target = "variant")
    @Mapping(source = "product", target = "product")
    @Mapping(target = "packageBuckets", ignore = true)
    @Mapping(target = "packageOrders", ignore = true)
    public abstract Package toSaveModel(PackageSaveDto packageSaveDto,
                                        Variant variant,
                                        Product product);

    public Package mapSaveModel(PackageSaveDto packageSaveDto, Product product) {
        Variant variant = variantRepository.findById(packageSaveDto.getVariantId()).orElseThrow();
        return toSaveModel(packageSaveDto, variant, product);
    }
}
