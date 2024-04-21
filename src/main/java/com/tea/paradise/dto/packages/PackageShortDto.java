package com.tea.paradise.dto.packages;

import com.tea.paradise.enums.VariantType;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageShortDto {
    private Long id;
    private VariantType variantType;
    private Double price;
}
