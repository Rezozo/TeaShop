package com.tea.paradise.dto.product;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAccountingDto {
    private Long id;
    private String title;
    private Integer orderCount;
    private Integer quantity;
    private Boolean active;
}
