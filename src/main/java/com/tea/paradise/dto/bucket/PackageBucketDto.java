package com.tea.paradise.dto.bucket;

import com.tea.paradise.dto.variant.VariantDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackageBucketDto {
    private Long packId;
    private VariantDto variant;
    private Double price;
    private ProductBucketDto product;
    private Integer plusTeaBonuses;
    private Integer quantityInBucket;
}
