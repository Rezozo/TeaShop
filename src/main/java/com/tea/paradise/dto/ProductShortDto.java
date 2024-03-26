package com.tea.paradise.dto;

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
    private List<ImageDto> images;
    private boolean isFavorite;
    private List<PackageShortDto> packages;
    private String title;
    private Short discount;
    private Integer countOfReviews;
    private Integer averageRating;
}
