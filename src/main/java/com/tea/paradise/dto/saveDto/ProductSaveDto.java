package com.tea.paradise.dto.saveDto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSaveDto {
    private Long id;
    private List<Long> images;
    private List<PackageSaveDto> packages;
    private Integer categoryId;
    private String article;
    private String title;
    private String description;
    private Short discount;
}
