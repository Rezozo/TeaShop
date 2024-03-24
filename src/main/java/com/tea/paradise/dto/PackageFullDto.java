package com.tea.paradise.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageFullDto {
    private Long id;
    private ProductShortDto product;
    private VariantDto variant;
    private Integer quantity;
    private Double price;
}
