package com.tea.paradise.dto;

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
