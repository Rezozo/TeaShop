package com.tea.paradise.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductFullDto {
    private Long id;
    private List<ImageDto> images;
    private List<PackageProductDto> packages;
    private CategoryDto category;
    private String article;
    private String title;
    private String description;
    private Short discount;
}
