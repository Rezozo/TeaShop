package com.tea.paradise.dto.saveDto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageSaveDto {
    private Integer variantId;
    private Integer quantity;
    private Double price;
}
