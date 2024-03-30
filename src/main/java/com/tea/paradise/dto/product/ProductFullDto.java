package com.tea.paradise.dto.product;

import com.tea.paradise.dto.category.CategoryDto;
import com.tea.paradise.dto.image.ImageDto;
import com.tea.paradise.dto.packages.PackageProductDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFullDto {
    private Long id;
    private List<PackageProductDto> packages;
    private String article;
    private String title;
    private String description;
    private Short discount;
    private Integer countOfReviews;
    private Integer averageRating;
    private CategoryDto category;
    private List<ImageDto> images;
}
