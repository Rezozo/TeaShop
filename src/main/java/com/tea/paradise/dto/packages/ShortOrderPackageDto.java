package com.tea.paradise.dto.packages;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortOrderPackageDto {
    private Long packageId;
    private Integer count;
}
