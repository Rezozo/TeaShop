package com.tea.paradise.dto.product;

import com.tea.paradise.dto.image.ImageDto;
import com.tea.paradise.dto.packages.PackageShortDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductShortDto {
    private Long id;
    private String article;
    private boolean isFavorite;
    private List<PackageShortDto> packages;
    private String title;
    private Short discount;
    private Integer countOfReviews;
    private Double averageRating;
    private List<ImageDto> images;
}
