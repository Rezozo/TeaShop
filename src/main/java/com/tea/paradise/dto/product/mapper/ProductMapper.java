package com.tea.paradise.dto.product.mapper;

import com.tea.paradise.dto.packages.PackageShortDto;
import com.tea.paradise.dto.packages.mapper.PackageProductMapper;
import com.tea.paradise.dto.product.ProductFullDto;
import com.tea.paradise.dto.product.ProductShortDto;
import com.tea.paradise.dto.saveDto.ProductSaveDto;
import com.tea.paradise.model.*;
import com.tea.paradise.model.Package;
import com.tea.paradise.repository.CategoryRepository;
import com.tea.paradise.repository.ImagesRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Mapper
public abstract class ProductMapper {
    @Autowired
    PackageProductMapper packageMapper;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImagesRepository imagesRepository;

    @Mapping(source = "packages", target = "packages")
    public abstract ProductShortDto toShortDto(Product product,
                                               boolean isFavorite,
                                               List<PackageShortDto> packages,
                                               int countOfReviews,
                                               double averageRating);

    @Mapping(source = "product.id", target = "id")
    public abstract ProductFullDto toFullDto(Product product,
                                             int countOfReviews,
                                             double averageRating);

    public ProductShortDto mapShortDto(Product product, Long userId) {
        boolean isFavorite = product.getFavorite_users().stream()
                .anyMatch(users -> Objects.equals(users.getId(), userId));
        List<PackageShortDto> packageShortDtos = product.getPackages().stream()
                .map(aPackage -> packageMapper.toShortDto(aPackage))
                .toList();
        List<Review> reviews = product.getReviews();
        int countOfReviews = Optional.ofNullable(reviews)
                .map(List::size)
                .orElse(0);
        double averageRating = Optional.ofNullable(reviews)
                .map(reviewList -> reviewList.stream()
                        .mapToDouble(Review::getStars)
                        .average()
                        .orElse(0.0))
                .orElse(0.0);

        return toShortDto(product, isFavorite, packageShortDtos, countOfReviews, averageRating);
    }

    public ProductFullDto mapFullDto(Product product) {
        List<Review> reviews = product.getReviews();
        int countOfReviews = Optional.ofNullable(reviews)
                .map(List::size)
                .orElse(0);
        double averageRating = Optional.ofNullable(reviews)
                .map(reviewList -> reviewList.stream()
                        .mapToDouble(Review::getStars)
                        .average()
                        .orElse(0.0))
                .orElse(0.0);
        return toFullDto(product, countOfReviews, averageRating);
    }

    @Mapping(target = "favorite_users", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "category.products", ignore = true)
    @Mapping(target = "category.parentCategory", ignore = true)
    @Mapping(source = "packages", target = "packages")
    public abstract Product toFullModel(ProductFullDto productFullDto,
                                        List<Package> packages);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "packages", ignore = true)
    @Mapping(target = "favorite_users", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(source = "images", target = "images")
    public abstract Product toSaveModel(ProductSaveDto saveDto,
                                        Category category,
                                        List<Image> images);

    public Product mapFullModel(ProductFullDto product) {
        List<Package> packages = product.getPackages().stream()
                .map(packageProductDto -> packageMapper.mapToProductPackage(packageProductDto))
                .toList();
        return toFullModel(product, packages);
    }

    public Product mapSaveModel(ProductSaveDto saveDto) {
        Category category = categoryRepository.findById(saveDto.getCategoryId()).orElseThrow();
        List<Image> images = imagesRepository.findAllByIdIn(saveDto.getImages());
        return toSaveModel(saveDto, category, images);
    }
}
