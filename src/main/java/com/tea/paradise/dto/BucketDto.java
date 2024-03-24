package com.tea.paradise.dto;

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
    private List<PackageFullDto> packages;
    private ZonedDateTime modifiedDate;
}
