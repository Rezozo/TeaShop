package com.tea.paradise.dto.mapper;

import com.tea.paradise.dto.ImageDto;
import com.tea.paradise.dto.PackageShortDto;
import com.tea.paradise.dto.ProductShortDto;
import com.tea.paradise.model.Product;
import com.tea.paradise.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

@Mapper
public abstract class ProductMapper {
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    PackageShortMapper packageMapper;

    @Mapping(source = "packages", target = "packages")
    public abstract ProductShortDto toShortDto(Product product,
                                               List<ImageDto> imageDtos,
                                               boolean isFavorite,
                                               List<PackageShortDto> packages,
                                               int countOfReviews,
                                               double averageRating);

    public ProductShortDto mapShortDto(Product product, Long userId) {
        List<ImageDto> imageDtos = product.getImages().stream()
                .map(image -> imageMapper.toDto(image))
                .toList();
        boolean isFavorite = product.getFavorite_users().stream()
                .anyMatch(users -> Objects.equals(users.getId(), userId));
        List<PackageShortDto> packageShortDtos = product.getPackages().stream()
                .map(aPackage -> packageMapper.toShortDto(aPackage))
                .toList();
        List<Review> reviews = product.getReviews();
        int countOfReviews = reviews.size();
        double averageRating = reviews.stream()
                .mapToDouble(Review::getStars)
                .average()
                .orElse(0.0);

        return toShortDto(product, imageDtos, isFavorite, packageShortDtos, countOfReviews, averageRating);
    }
}
