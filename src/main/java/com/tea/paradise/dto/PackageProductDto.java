package com.tea.paradise.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageProductDto {
    private Long id;
    private VariantDto variant;
    private Integer quantity;
    private Double price;
}
