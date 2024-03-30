package com.tea.paradise.dto.bucket;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BucketDto {
    private Long id;
    private Long userId;
    private Double totalSumWithDiscount;
    private Double totalDiscount;
    private Integer plusTeaBonuses;
    private List<PackageBucketDto> products;
    private ZonedDateTime modifiedDate;
}
