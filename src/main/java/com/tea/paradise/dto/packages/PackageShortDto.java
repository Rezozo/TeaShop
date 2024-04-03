package com.tea.paradise.dto.packages;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageShortDto {
    private Long id;
    private String variantName;
    private Double price;
}