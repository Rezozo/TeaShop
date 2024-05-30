package com.tea.paradise.dto.packages.mapper;

import com.tea.paradise.dto.packages.PackageOrderDto;
import com.tea.paradise.dto.product.ProductAccountingDto;
import com.tea.paradise.dto.product.mapper.ProductMapper;
import com.tea.paradise.dto.saveDto.PackageSaveDto;
import com.tea.paradise.dto.variant.mapper.VariantMapper;
import com.tea.paradise.model.*;
import com.tea.paradise.model.Package;
import com.tea.paradise.repository.VariantRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

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
        Variant variant = variantRepository.findByTitle(packageSaveDto.getVariant()).orElseThrow();
        return toSaveModel(packageSaveDto, variant, product);
    }

    @Mapping(source = "packageOrder.pack.product.title", target = "productTitle")
    public abstract PackageOrderDto toOrderDto(PackageOrder packageOrder,
                                               String imageUrl,
                                               String type,
                                               Double price);

    public PackageOrderDto mapToOrderDto(PackageOrder packageOrder) {
        String imageUrl = packageOrder.getPack().getProduct().getImages().stream()
                .findFirst().map(Image::getImageUrl)
                .orElseThrow();
        String type = packageOrder.getPack().getVariant().getTitle().name();
        Double price = packageOrder.getPack().getPrice();
        if (Objects.nonNull(packageOrder.getPack().getProduct().getDiscount())) {
            price -= packageOrder.getPack().getPrice() * ((double) packageOrder.getPack().getProduct().getDiscount() / 100);
        }
        return toOrderDto(packageOrder, imageUrl, type, price);
    }

    @Mapping(source = "quantity", target = "quantity")
    public abstract ProductAccountingDto toAccountingDto(Product product,
                                                         Integer orderCount,
                                                         Integer quantity,
                                                         String imageUrl);

    public ProductAccountingDto mapToAccountingDto(Product product) {
        List<Package> packageList = product.getPackages();
        Integer orderCount = packageList.stream()
                .mapToInt(value -> value.getPackageOrders().stream()
                        .mapToInt(PackageOrder::getQuantity)
                        .sum())
                .sum();
        Integer quantity = packageList.stream()
                .mapToInt(Package::getQuantity)
                .sum();
        return toAccountingDto(product, orderCount, quantity,
                product.getImages().stream()
                        .findFirst().orElseThrow().getImageUrl());
    }
}
