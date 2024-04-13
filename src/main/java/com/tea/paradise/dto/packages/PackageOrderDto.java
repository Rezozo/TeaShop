package com.tea.paradise.dto.packages;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageOrderDto {
    private String imageUrl;
    private String productTitle;
    private Double price;
    private String type;
    private Integer quantity;
}
