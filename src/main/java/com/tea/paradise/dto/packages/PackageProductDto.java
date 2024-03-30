package com.tea.paradise.dto.packages;

import com.tea.paradise.dto.variant.VariantDto;
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
